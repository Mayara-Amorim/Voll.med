package med.voll.api.pacientes;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.DadosAtualizarMedico;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Table(name="pacientes")
@Entity(name="Pacientes")
public class Paciente {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private String nome;
		private String email;
		private String telefone;
		private String cpf;
		private Boolean ativo;

		@Embedded
		private Endereco endereco;
		
		public Paciente(DadosPacientes dados) {this.ativo = true;
		 this.nome = dados.nome();
		 this.email = dados.email();
		 this.telefone = dados.telefone();
		 this.cpf = dados.cpf();
		 this.endereco = new Endereco(dados.endereco());

		
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelefone() {
			return telefone;
		}

		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

		public String getCpf() {
			return cpf;
		}

		public void setCpf(String cpf) {
			this.cpf = cpf;
		}

		public Endereco getEndereco() {
			return endereco;
		}

		public void setEndereco(Endereco endereco) {
			this.endereco = endereco;
		}
		public Boolean getAtivo() {
			return ativo;
		}
		public void excluir() {
			this.ativo = false;

		}

	public void atualizar(@Valid DadosAtualizarPaciente dados) {
		if(dados.nome()!= null) {
			this.nome = dados.nome();
		}
		if(dados.telefone()!= null) {
			this.telefone = dados.telefone();
		}
		if(dados.endereco()!= null) {
			this.endereco.atualizar(dados.endereco());;
		}

	}


		
		
		
}
