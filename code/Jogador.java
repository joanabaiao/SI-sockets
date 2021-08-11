public class Jogador {
	
	private String nome;
	private String password;
	private double[][] pontuacoes; // Tabela: numero discos | numero de jogos | numero movimentos | media de movimentos por jogo 
	
	public Jogador(String nome, String password) {
		this.nome = nome;
		this.password = password;
		this.pontuacoes = new double[8][4];
		
		for (int i = 0; i < 8; i++) {
			pontuacoes[i][0] = i+3; // cria a coluna com os numeros de discos (3 a 10)
			for (int j = 1; j < 4; j++) {
				pontuacoes[i][j] = 0; // os restantes valores sao nulos
			}
		}	
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double[][] getPontuacoes() {
		return pontuacoes;
	}

	public void setPontuacoes(double[][] pontuacoes) {
		this.pontuacoes = pontuacoes;
	}
	
	// ATUALIZAR OS PONTOS DO JOGADOR NO FINAL DE UM JOGO
	public void atualizarpontos(int ndiscos, int totalmov) {	
		pontuacoes[ndiscos - 3][1] = pontuacoes[ndiscos - 3][1] + 1; // atualizar numero de jogos
		pontuacoes[ndiscos - 3][2] = pontuacoes[ndiscos - 3][2] + totalmov; // atualizar numero de movimentos
		pontuacoes[ndiscos - 3][3] = pontuacoes[ndiscos - 3][2] / pontuacoes[ndiscos - 3][1]; // atualizar media de movimentos por jogo
	}
		
	// ESTATISTICAS DO JOGADOR
	public String estatistica() {
		String s = "";
		for (int i = 0; i < 8; i++) {
			if (pontuacoes[i][1] != 0) {
				s = s + RowtoString(i) + "  ";
			}
		}
		return s;
	}
	
	// CONVERTER A LINHA DA TABELA PONTUACOES PARA STRING
	private String RowtoString(int i) {
		String s = pontuacoes[i][0] + "-" + pontuacoes[i][1] + "-" +pontuacoes[i][3]; // discos + jogos + media
		return s;
	}
	
}