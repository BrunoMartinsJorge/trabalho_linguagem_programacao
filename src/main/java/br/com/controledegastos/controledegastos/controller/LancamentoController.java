package br.com.controledegastos.controledegastos.controller;

import br.com.controledegastos.controledegastos.enums.TipoLancamento;
import br.com.controledegastos.controledegastos.model.Lancamento;
import br.com.controledegastos.controledegastos.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class LancamentoController {
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);
        model.addAttribute("novoLancamento", new Lancamento());
        model.addAttribute("tipos", TipoLancamento.values());
        return "index"; // Retorna o arquivo templates/index.html
    }

    // Método para adicionar um novo lançamento (via htmx)
    @PostMapping("/lancamentos")
    public String addLancamento(@ModelAttribute Lancamento novoLancamento, Model model) {
        lancamentoRepository.save(novoLancamento);

        // Após salvar, recarregamos a lista e a retornamos como um fragmento
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);

        // Retorna apenas o fragmento da tabela, não a página inteira
        return "index :: lista-lancamentos";
    }

    // Método para excluir um lançamento (via htmx)
    @DeleteMapping("/lancamentos/{id}")
    public String deleteLancamento(@PathVariable Long id, Model model) {
        lancamentoRepository.deleteById(id);

        // Após deletar, recarregamos a lista e a retornamos como um fragmento
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);

        // Retorna apenas o fragmento da tabela
        return "index :: lista-lancamentos";
    }
}
