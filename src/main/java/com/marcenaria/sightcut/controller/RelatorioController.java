package com.marcenaria.sightcut.controller;

import com.marcenaria.sightcut.service.RelatorioService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<byte[]> gerarRelatorioProjeto(@PathVariable Long projetoId) {
        try {
            String caminhoArquivo = service.gerarPdfProjeto(projetoId);
            Path path = Path.of(caminhoArquivo);
            byte[] pdfBytes = Files.readAllBytes(path);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename(path.getFileName().toString())
                            .build()
            );

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/gerar")
    public ResponseEntity<byte[]> gerar(
            @RequestParam String titulo,
            @RequestParam String conteudo
    ) {
        try {
            String path = "relatorios/" + titulo.replaceAll("\\s+", "_") + ".pdf";
            byte[] pdf = Files.readAllBytes(Path.of(path));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + Path.of(path).getFileName())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}