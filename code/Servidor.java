import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	public static void main(String args[]) throws IOException {
		
		ServerSocket s = new ServerSocket(1234);

		ArrayList<Jogador> listalogin = new ArrayList<>();
		listalogin.add(new Jogador("aluno", "12345"));
		listalogin.add(new Jogador("professor", "abcde"));

		System.out.println("A espera de um cliente...");
		
		while (true) 
		{
			Socket s1 = s.accept();
			InputStream in = s1.getInputStream();
			DataInputStream dataIn = new DataInputStream(in);
			OutputStream out = s1.getOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);
			System.out.println("\nCliente conectado!!!");
					
			boolean correr = true;
			while (correr == true) {
				
				Jogador jogador = null;
				String pedido;	
				
				String nome = dataIn.readUTF(); // 1
				int index = -1;
				
				for (Jogador j : listalogin) {
					if (j.getNome().equals(nome) == true) {
						index = listalogin.indexOf(j);
						jogador = j;
					}
				}
				
				if (index == -1) {
					dataOut.writeUTF("false"); // 1
					dataOut.flush();
					System.out.println("O utilizador nao existe!");
				}
				
				else if (index != -1) {
					dataOut.writeUTF("true"); // 1
					dataOut.flush();
					System.out.println("O utilizador existe!");
				}
				
				String password = dataIn.readUTF(); // 2
				
				if (index != -1 && listalogin.get(index).getPassword().equals(password)) {
					dataOut.writeUTF("true"); // 2
					dataOut.flush();
					System.out.println("Palavra-passe correta!\nO cliente iniciou sessao!");
					
					// CICLO JOGO
					boolean jogar = true;
					while (jogar == true) 
					{	
						Jogo jogo = null;
						System.out.println("\nA espera de informacao sobre os pinos...");
						
						// NUMERO DE DISCOS - 3 a 10 discos
						int ndiscos = 0;
						while ((ndiscos < 3 | ndiscos > 10)) 
						{
							pedido = dataIn.readUTF(); // 3
							if (isInt(pedido) == true) {
								ndiscos = Integer.parseInt(pedido);
							}
							if (isInt(pedido) == true && ndiscos >=3 && ndiscos <=10) {
								ndiscos = Integer.parseInt(pedido);
								dataOut.writeUTF("true"); // 3
								dataOut.flush();
								System.out.println("Numero de discos escolhido: " + ndiscos);
							}
							else {
								dataOut.writeUTF("false"); // 3
								dataOut.flush();
							}
						}

						// PINO INICIAL
						String pinoinicial = "0";
						while (!pinoinicial.equals("A") && !pinoinicial.equals("B") && !pinoinicial.equals("C")) 
						{
							pinoinicial = dataIn.readUTF(); // 4
							if (!pinoinicial.equals("A") && !pinoinicial.equals("B") && !pinoinicial.equals("C")) {
								dataOut.writeUTF("false"); // 4
								dataOut.flush();
							}
							else {
								dataOut.writeUTF("true"); // 4
								dataOut.flush();
								System.out.println("Pino inicial escolhido: " + pinoinicial);
							}
						}
						
						// PINO FINAL
						String pinodestino = "0";
						while ((!pinodestino.equals("A") && !pinodestino.equals("B") && !pinodestino.equals("C")) | pinodestino.equals(pinoinicial)) 
						{
							pinodestino = dataIn.readUTF(); // 5
							if (pinodestino.equals(pinoinicial)) { // o pino final nao pode ser o mesmo que o pino inicial
								dataOut.writeUTF("false1"); // 5
								dataOut.flush();
							}
							else if (!pinodestino.equals("A") && !pinodestino.equals("B") && !pinodestino.equals("C")) {
								dataOut.writeUTF("false2"); // 5
								dataOut.flush();
							}
							else {
								System.out.println("Pino destino escolhido: " + pinodestino);
								jogo = new Jogo(ndiscos, pinoinicial, pinodestino);	
								dataOut.writeUTF(jogo.pinosABC(false)); // 5
							}
						}
						
						// JOGO
						System.out.println("\nPronto para jogar! A espera de instrucoes...");
						String resposta = "";
						String jogada = "";
						
						boolean desistir = false;
						boolean continuar = true;
						while (continuar == true && desistir == false)
						{
							jogada = dataIn.readUTF(); // 6
							
							// MOVIMENTO 1 (A->B)
							if (jogada.equals("1")) {
								System.out.println("Recebi uma instrucao: A->B");
								resposta = jogo.jogar(1); 
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 6
								dataOut.flush();
							}
							
							// MOVIMENTO 2 (A->C)
							else if (jogada.equals("2"))  {
								System.out.println("Recebi uma instrucao: A->C");
								resposta = jogo.jogar(2); 
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 6
								dataOut.flush();
							}
							
							// MOVIMENTO 3 (B->A)
							else if (jogada.equals("3"))  {
								System.out.println("Recebi uma instrucao: B->A");
								resposta = jogo.jogar(3); 
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 6
								dataOut.flush();
							}
							
							// MOVIMENTO 4 (B->C)
							else if (jogada.equals("4"))  {
								System.out.println("Recebi uma instrucao: B->C");
								resposta = jogo.jogar(4); 
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 8
								dataOut.flush();
							}
							
							// MOVIMENTO 5 (C->A)
							else if (jogada.equals("5"))  {
								System.out.println("Recebi uma instrucao: C->A");
								resposta = jogo.jogar(5);
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 6
								dataOut.flush();
							}
							
							// MOVIMENTO 6 (C->B)
							else if (jogada.equals("6"))  {
								System.out.println("Recebi uma instrucao: C->B");
								resposta = jogo.jogar(6);
								continuar = isFinal(resposta);
								dataOut.writeUTF(resposta); // 6
								dataOut.flush();
							}
							
							// SAIR (Y)
							else if (jogada.equals("Y")) {
								System.out.println("Recebi uma instrucao: Desistir");
								System.out.println("\nO cliente desistiu do jogo!");
								dataOut.writeUTF("desistir"); // 6
								dataOut.flush();
								desistir = true;
								
							}

							// OPCAO INVALIDA
							else {
								System.out.println("Recebi uma instrucao invalida");
								dataOut.writeUTF("false"); // 6
								dataOut.flush();
							}
							
							dataOut.writeUTF(jogo.pinosABC(true)); // 6
							dataOut.flush();
						}
					
						if (desistir == false) {
							System.out.println("\nBoa! O cliente acabou o jogo!!!");
							jogador.atualizarpontos(ndiscos, jogo.getTotalmov()); // quando o utilizador nao desiste os pontos sao atualizados
						}
						
						// MENU FINAL - Quando o jogador ganha ou desiste
						pedido = "0";
						while (!pedido.equals("1") && !pedido.equals("S")) {
							System.out.println("\nA espera de instrucoes...");
							pedido = dataIn.readUTF(); // 7
							if (pedido.equals("1")) {
								dataOut.writeUTF("continuar"); // 7
								dataOut.flush();
								System.out.println("Recebi uma instrucao: Jogar novamente");
							}
							else if (pedido.equals("S")) {
								dataOut.writeUTF("terminar"); // 7
								dataOut.flush();
								jogar = false;
								correr = false;
								System.out.println("Recebi uma instrucao: Sair");
							}
							else if (pedido.equals("2")) {
								dataOut.writeUTF(jogador.estatistica()); // 7
								dataOut.flush();
								System.out.println("Recebi uma instrucao: Mostrar estatisticas");
							}	
							else {
								dataOut.writeUTF("false"); // 7
								dataOut.flush();
							}
						}
					}
					
				}
				
				else {
					correr = false;				
					dataOut.writeUTF("false"); // 2
					dataOut.flush();
					System.out.println("Palavra-passe errada!");
				}
			}
					
			dataIn.close();
			dataOut.close();
			s1.close();
			System.out.println("\nCliente desconectado!!!");
		}
	}
	////////////////////////////METODOS //////////////////////////// 
	
	// VERIFICAR SE O NUMERO DE DISCOS E INTEIRO
	public static boolean isInt(String request) {
		boolean inteiro = true;
			try {
				Integer.parseInt(request);
			} 
			catch (NumberFormatException e) {
				inteiro = false;
			}
			return inteiro;
	}

	// VERIFICAR SE O JOGO JA ACABOU
	public static boolean isFinal(String resposta) {
		boolean continuar = true;
		if (resposta.contains("acabar")) {
			continuar = false;
		}
		return continuar;	
	}

}	