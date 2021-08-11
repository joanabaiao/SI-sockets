public class Jogo {
	
	private Pino pinoA;
	private Pino pinoB;
	private Pino pinoC;
	
	private String pinodestino;
	private int ndiscos;
	private int totalmov; // total de movimentos realizados ao longo do jogo
	private int min; // numero minimo de jogadas para o numero de discos
	
	public Jogo(int ndiscos, String pinoinicial, String pinodestino) 
	{
		this.ndiscos = ndiscos;
		this.pinodestino = pinodestino;
		this.min = (int) (Math.pow(2, ndiscos) - 1);
		this.totalmov = 0;
		
		this.pinoA = new Pino(ndiscos, "A", pinoinicial);
		this.pinoB = new Pino(ndiscos, "B", pinoinicial);
		this.pinoC = new Pino(ndiscos, "C", pinoinicial);
	}

	public Pino getPinoA() {
		return pinoA;
	}
	public void setPinoA(Pino pinoA) {
		this.pinoA = pinoA;
	}

	public Pino getPinoB() {
		return pinoB;
	}
	public void setPinoB(Pino pinoB) {
		this.pinoB = pinoB;
	}

	public Pino getPinoC() {
		return pinoC;
	}
	public void setPinoC(Pino pinoC) {
		this.pinoC = pinoC;
	}

	public String getPinodestino() {
		return pinodestino;
	}
	public void setPinodestino(String pinodestino) {
		this.pinodestino = pinodestino;
	}

	public int getNdiscos() {
		return ndiscos;
	}
	public void setNdiscos(int ndiscos) {
		this.ndiscos = ndiscos;
	}

	public int getTotalmov() {
		return totalmov;
	}
	public void setTotalmov(int totalmov) {
		this.totalmov = totalmov;
	}

	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}

	// JOGAR
	public String jogar(int n) {
		String resposta = "";
		if (n == 1) {
			resposta = jogada(pinoA, pinoB); // Movimento 1 (A->B)
		}
		else if (n == 2) {
			resposta = jogada(pinoA, pinoC); // Movimento 2 (A->C)
		}
		else if (n == 3) {
			resposta = jogada(pinoB, pinoA); // Movimento 3 (B->A)
		}
		else if (n == 4) {
			resposta = jogada(pinoB, pinoC); // Movimento 4 (B->C)
		}
		else if (n == 5) {
			resposta = jogada(pinoC, pinoA); // Movimento 5 (C->A)
		}
		else if (n == 6) {
			resposta = jogada(pinoC, pinoB); // Movimento 6 (C->B)
		}	
		return resposta;
	}
	
	private String jogada(Pino pinoI, Pino pinoF) {	
		String resposta = "";
		if (movimento(pinoI, pinoF) == true) {
			if (nfinal() == ndiscos) {
				if (totalmov == min) {
					resposta = "acabar1" + "-" + totalmov;  // 8
				}
				else {
					resposta = "acabar2" + "-" + totalmov + "-" + ndiscos + "-" + min + "-" + (totalmov - min);  // 8
				}
			}
			else {
				resposta = "continuar";  // 8
			}
		}
		else if (isVazio(pinoI.getPino()) == true) {
			resposta = "invalido1";  // 8
		}
		else {
			resposta = "invalido2";  // 8
		}
		return resposta;
	}
	
	// MOVIMENTOS (pino inicial I -> pino final F) - verifica se o movimento e possivel! Em caso afirmativo, move o disco e devolve true
	private boolean movimento(Pino pinoI, Pino pinoF)
	{
		boolean movimentoIF = false;
		int linhabaixo = (pinoF.getPino().length - 4 - pinoF.getNdiscos());
		
		for (int i = 0; i < pinoI.getPino().length - 2; i++) {
			if (pinoI.getPino()[i] != 0 && pinoF.getPino()[linhabaixo] == 0) {
				
				if((pinoF.getPino()[linhabaixo + 1] > pinoI.getPino()[i]) || pinoF.getPino()[pinoF.getPino().length - 4] == 0) {
					movimentoIF = true;
					int troca = pinoI.getPino()[i];
					pinoI.getPino()[i] = 0;
					pinoF.getPino()[linhabaixo] = troca;
					
					setTotalmov(totalmov + 1);
					pinoI.setNdiscos(pinoI.getNdiscos() - 1);
					pinoF.setNdiscos(pinoF.getNdiscos() + 1);				
				}
			}
		}
		return movimentoIF;
	}
		
	// VERIFICAR SE O PINO ESTA VAZIO
	private boolean isVazio(int[] pino) {
		int soma = 0;
		boolean vazio = false;
		for (int i = 0; i < pino.length; i++) {
			soma = soma + pino[i];
		}
		if (soma == 0) {
			vazio = true;
		}
		return vazio;
	}	
	
	// NUMERO DE DISCOS NO PINO FINAL
	private int nfinal() {
		int n = 0;
		if(pinodestino.equals("A")) {
			n = pinoA.getNdiscos();
		}
		else if(pinodestino.equals("B")) {
			n = pinoB.getNdiscos();
		}
		else if(pinodestino.equals("C")) {
			n = pinoC.getNdiscos();
		}
		return n;
	}
	
	// CRIAR UMA STRING COM OS PINOS + MOVIMENTOS: enviada do servidor para o cliente
	public String pinosABC(boolean jogo) {
		String s = pinoA.ArraytoString() + "  " + pinoB.ArraytoString() + "  " + pinoC.ArraytoString() + "  " + pinodestino;
		if (jogo == true) {
			s = s + "  " + totalmov; // durante o jogo e apresentado o numero de movimentos realizados
		}
		else {
			s = s + "  " + min; // antes de comecar o jogo e apresentado o numero minimo de movimentos
		}	
		return s;	
	}
	
}