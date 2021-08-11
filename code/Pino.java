public class Pino {
	
	private int[] pino;    // array do pino - o numero corresponde ao tamanho dos discos
	private int ndiscos;   // numero de discos presentes no pino
	
	public Pino(int ndiscos, String letra, String pinoinicial) {
		this.pino = new int[ndiscos + 4];
		
		if (letra.equals(pinoinicial)) {
			this.ndiscos = ndiscos; // se for o pino inicial fica com todos os discos
			criarpino(true);
		}
		else {
			this.ndiscos = 0; // caso contrario nao tem nenhum disco
			criarpino(false);
		}
	}

	public int[] getPino() {
		return pino;
	}

	public void setPino(int[] pino) {
		this.pino = pino;
	}

	public int getNdiscos() {
		return ndiscos;
	}

	public void setNdiscos(int ndiscos) {
		this.ndiscos = ndiscos;
	}
	
	// CRIAR PINO
	private void criarpino(boolean inicial) {
		for (int i = 0; i < pino.length; i++) {
			if (inicial == true) {
				if (i == 0 || i == pino.length - 1 || i == pino.length - 2 || i == pino.length - 3) { // para o topo, base e identificacao do pino
					this.pino[i] = 0;
				}
				else {
					this.pino[i] = (2*(i+1))-1; // discos
				}
			}
			else {
				this.pino[i] = 0;
			}
		}
	}
	
	// CRIAR UMA STRING COM OS NUMEROS DO ARRAY DO PINO 
	public String ArraytoString () {
		String s = "";
		for (int i = 0; i < pino.length; i++) {
			s = s + pino[i] + "-";
		}	
		return s;
	}

}
