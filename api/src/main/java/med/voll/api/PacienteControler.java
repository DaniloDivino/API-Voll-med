package med.voll.api;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteControler {
    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastar(@RequestBody @Valid DadosCadastroPacientes dadosPaciente){
        repository.save(new Paciente(dadosPaciente));
    }

    @GetMapping
    public Page<DadosListagemPacientes> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repository.findAll(paginacao).map(DadosListagemPacientes::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoPacientes dadosPaciente){
        var paciente = repository.getReferenceById(dadosPaciente.id());
        paciente.atualizarInformacoes(dadosPaciente);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.deletar();
    }
}
