package med.voll.api.endereco;

import jakarta.persistence.Embeddable;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable

@NoArgsConstructor
@AllArgsConstructor
public class Endereco {
	 
	private String logradouro;
	 private String bairro;
	 private String cep;
	 private String cidade;
	 private String uf; 
	 private String complemento; 
	 private String numero;

	public Endereco(DadosEndereco dados) {
		this.logradouro = dados.logradouro();
		this.bairro = dados.bairro();
		this.cep = dados.cep();
		this.cidade = dados.cidade();
		this.uf = dados.uf();
		this.complemento = dados.complemento();
		this.numero = dados.numero();
	}

	public void atualizar(@Valid DadosEndereco dados) {
		if(dados.logradouro() != null) {
			this.logradouro = dados.logradouro();
		}
		if(dados.bairro() != null) {
			this.bairro = dados.bairro();
		}
		if(dados.cep() != null) {
			this.cep = dados.cep();
		}
		if(dados.cidade() != null) {
			this.cidade = dados.cidade();
		}
		if(dados.uf() != null) {
			this.uf= dados.uf();
		}
		if(dados.complemento() != null) {
			this.complemento = dados.complemento();
		}
		if(dados.numero() != null) {
			this.numero = dados.numero();
		}
		
		
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
