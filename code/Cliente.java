import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	public static void main(String args[]) throws IOException {
		
		Scanner scan = new Scanner(System.in);
		
		String comando = " ";
		String valido = " ";
		String resposta = " ";
		String esquema = " ";
		
		System.out.println("******************** TORRE DE HANOI *******************");
		boolean menu = true;
		
		while (menu == true) {
			System.out.println("\nIniciar conexao com o servidor Hanoi? [S/N]");
			comando = scan.nextLine();
			
			if (comando.equals("N")) {
				menu = false;
				System.out.println("--------- Nao se conectou. Adeus! ---------");
			}
		
			else if (comando.equals("S")) {
				
				boolean conectado = true;	
				while (conectado == true) {
					
					Socket socket = new Socket("localhost", 1234);
					InputStream in = socket.getInputStream();
					DataInputStream dataIn = new DataInputStream(in);
					OutputStream out = socket.getOutputStream();
					DataOutputStream dataOut = new DataOutputStream(out);	
					
					System.out.println("\n************************ LOGIN ************************");
					
					System.out.println("\nInsira o nome de utilizador: ");
					String nome = scan.nextLine();
					dataOut.writeUTF(nome); // 1
					dataOut.flush();
					String validarnome = dataIn.readUTF(); // 1
					
					System.out.println("Insira a palavra-passe: ");
					String pass = scan.nextLine();
					dataOut.writeUTF(pass); // 2
					dataOut.flush();
					valido = dataIn.readUTF(); // 2
					
					if (validarnome.equals("false")) {
						System.out.println("ERRO: O utilizador nao existe!");
					}
					else if (valido.equals("false")) {
						System.out.println("ERRO: A palavra-passe esta errada!");
					}
					
					if (valido.equals("true")) {
						
						// CICLO JOGO
						boolean terminar = false;
						while (terminar == false)
						{
							System.out.println("\n********************** NOVO JOGO **********************");
							// PEDIR NUMERO DE DISCOS - De 3 a 10 discos
							valido = "false"; 
							while (valido.equals("false")) {
								System.out.println("\nDefina o numero de discos (3 a 10): ");
								comando = scan.nextLine();
								dataOut.writeUTF(comando); // 3
								dataOut.flush();
								valido = dataIn.readUTF(); // 3
								if (valido.equals("false")) {
									System.out.println("ERRO: Opcao invalida! Deve introduzir um número inteiro de 3 a 10.");
								}
							}
							
							// PEDIR PINO INICIAL - Pode ser A, B ou C
							valido = "false"; 
							while (valido.equals("false")) {
								System.out.println("\nDefina o pino inicial (A, B ou C): ");
								comando = scan.nextLine();
								dataOut.writeUTF(comando); // 4
								dataOut.flush();
								valido = dataIn.readUTF(); // 4
								if (valido.equals("false")) {
									System.out.println("ERRO: Opcao invalida! Deve introduzir A, B ou C.");
								}
							}
							
							// PEDIR PINO FINAL - Nao pode ser igual ao pino inicial
							valido = "false1"; 
							while (valido.equals("false1") | valido.equals("false2")) 
							{
								System.out.println("\nDefina o pino destino (A, B ou C): ");
								comando = scan.nextLine();
								dataOut.writeUTF(comando); // 5
								dataOut.flush();
								valido = dataIn.readUTF(); // 5
								esquema = valido;
								if (valido.equals("false1")) {
									System.out.println("ERRO: Opção invalida! O pino destino tem que ser diferente do pino inicial.");
								}
								else if (valido.equals("false2")) {
									System.out.println("ERRO: Opcao invalida! Deve introduzir A, B ou C");
								}	
							}
							
							System.out.println("\n--------------------------------------------------------");
							System.out.println("\n" + esquemaABC(esquema, false));
							
							// JOGO
							resposta = "continuar";		
							while (resposta.contains("acabar") == false && !resposta.equals("desistir")) 
							{ 
								System.out.println("Escolha um movimento possivel: ");
								System.out.println("1: A->B      2: A->C      3: B->A      4: B->C      5: C->A      6: C->B      Y: Sair");
								comando = scan.nextLine();
								dataOut.writeUTF(comando); // 6
								dataOut.flush();
								
								resposta = dataIn.readUTF(); // 6
								esquema = dataIn.readUTF(); // 6	
								
								if (resposta.equals("false")) {
									System.out.println("ERRO: Opcao invalida! Deve introduzir um numero de 1 a 6 ou Y");
									System.out.println("\n" + esquemaABC(esquema, true));
								}
								else if (resposta.equals("continuar")){
									System.out.println("Jogou!");
									System.out.println("\n" + esquemaABC(esquema, true));
								}
								else if (resposta.equals("invalido1")) {
									System.out.println("ERRO: Movimento invalido! O pino nao tem discos.");
									System.out.println("\n" + esquemaABC(esquema, true));
								}	
								else if (resposta.equals("invalido2")) {
									System.out.println("ERRO: Movimento invalido! Nao e possivel colocar um disco maior sobre um menor.");
									System.out.println("\n" + esquemaABC(esquema, true));
								}	
								else if (resposta.equals("desistir")) {
									System.out.println("Desistiu! Jogo terminado.");
								}
								else {
									System.out.println("Jogou!");
									System.out.println("\n" + esquemaABC(esquema, true));
									System.out.println("-------------------------------------------------------\n");
									System.out.println(mensagemfinal(resposta));			
								}
							}
							
							System.out.println("\n********************* MENU FINAL *********************");
							
							resposta = "false";
							while (!resposta.equals("terminar") && !resposta.equals("continuar")) {
								System.out.println("\n1- Novo jogo \n2- Ver estatisticas \nS- Sair \nEscolha uma opcao:");
								comando = scan.nextLine();
								dataOut.writeUTF(comando); // 7
								dataOut.flush();
								resposta = dataIn.readUTF(); // 7
								if (resposta.equals("false")) {
									System.out.println("ERRO: Opcao invalida! Deve introduzir 1, 2 ou Q."); //S!!!
								}
								else if (resposta.equals("terminar")){
									System.out.println("Terminou sessao!\n");
									terminar = true;
									conectado = false;
									System.out.println("******************** TORRE DE HANOI *******************");						
								}
								else if (!resposta.equals("false") && !resposta.equals("continuar") && !resposta.equals("terminar")) {
									System.out.println(estatistica(resposta)); // estatisticas
								}
							}
						}
						dataIn.close();
						dataOut.close();
						socket.close();
					}
	
					else {	
						dataIn.close();
						dataOut.close();
						socket.close();
						
						comando = "0";
						while (!comando.equals("1") && !comando.equals("2")) {
							System.out.println("\n1- Tentar novamente \n2- Sair \nEscolha uma opcao:");
							comando = scan.nextLine();
							if (comando.equals("2")) {
								System.out.println("----------- Adeus! -----------\n");
								menu = false;
								conectado = false;
							}
							else if (!comando.equals("1")) {
								System.out.println("ERRO: Opcao invalida! Deve introduzir 1 ou 2.");
							}
						}
					}
				}
			}	
		
			else {
				System.out.println("ERRO: Opcao invalida! Deve introduzir S ou N.");
			}
		}
		
		scan.close();
	}
	
	//////////////////////////// METODOS //////////////////////////// 
	
	// ESQUEMA DO JOGO
	private static String esquemaABC(String esquema, boolean jogo) {
		String[] s = esquema.split("  "); 
		String[] pinoA = s[0].split("-");
		String[] pinoB = s[1].split("-");
		String[] pinoC = s[2].split("-");
		String destino = s[3];
		String mov = s[4];
		
		String frase ;
		if (jogo == true) {
			frase = "Numero de movimentos realizados: " + mov + "\n\n";	
		}
		else {
			frase = "O numero minimo de jogadas e " + mov + ".\n\n";
		}
		
		String pinos = "";		
		for (int i = 0; i < pinoA.length; i++) {		
			pinos = pinos + esquemapino(pinoA, i, "A", destino) + esquemapino(pinoB, i, "B", destino) + esquemapino(pinoC, i, "C", destino) + "\n";	
		}
		
		return (frase + pinos);
	}
	
	private static String esquemapino(String[] pino, int i, String letra, String destino) {
		String esquema = "";	
		int ndiscos = pino.length - 4;
		int colunas = 2*(ndiscos) + 3;		
		for (int j = 0; j < colunas; j++) {
			if (i == pino.length-1) {
				if (letra.equals(destino)){
					if (j == ndiscos - 2) {
						esquema = esquema + "destino";	
					}
					else if (!(j >= (colunas-1)/2 - 3 && j <= (colunas-1)/2 + 3)) {
						esquema = esquema + " ";
					}
				}
				else {
					esquema = esquema + " ";
				}
			}	
			else if (i == pino.length - 2) {
				if (j == (colunas-1)/2) {
					esquema = esquema + letra;
				}
				else {
					esquema = esquema + " ";
				}
			}
			else if (i == pino.length - 3) {
				esquema = esquema + "#";
			}	
			else if (Integer.parseInt(pino[i]) == 0) {
				if (j == (colunas-1)/2) {
					esquema = esquema + "|";
				}
				else {
					esquema = esquema + " ";
				}
			}	
			else {
				int k = (colunas - Integer.parseInt(pino[i]))/2;
				if (j < k || j >= (Integer.parseInt(pino[i]) + k)) {
					esquema = esquema + " ";
				}
				else {
					esquema = esquema + "*";
				}
			}
		}
		return esquema;
	}
	
	// ESTATISTICAS DO JOGADOR
	private static String estatistica(String resposta) {	
		String frase = "\nEstatisticas: ";
		if (resposta.length() == 0) {
			frase = frase + "Sem dados estatisticos.";
		}		
		else {
			String[] s = resposta.split("  ");
			for (int i = 0; i < s.length; i++) {
				String[] n = s[i].split("-");
				int ndiscos = (int) Double.parseDouble(n[0]);
				int njogos = (int) Double.parseDouble(n[1]);
				double media = Double.parseDouble(n[2]);
				double scale = Math.pow(10, 2);
				frase = frase + "\nPara " +  ndiscos + " discos, a media de movimentos em " + njogos + " jogo(s) é " +  Math.round(media*scale)/scale + ".";
			}
		}
		return frase;
	}
	
	// MENSAGEM FINAL DO JOGO
	private static String mensagemfinal(String resposta) {
		String[] s = resposta.split("-");
		String frase = "PARABENS, ACABOU O JOGO! Realizou " + s[1] + " movimentos.\n"; 
		
		if (resposta.contains("acabar1")) { // acabou com o numero minimo de movimentos
			frase = frase + "\nCompletou o jogo com o numero minimo de jogadas!";
		}
		else { 
			frase = frase + "\nO numero minimo de jogadas para " + s[2] + " discos e "  + s[3] + ".\nRealizou mais " + s[4] + " jogadas que o ideal.";
		}
		return frase;
	}

}