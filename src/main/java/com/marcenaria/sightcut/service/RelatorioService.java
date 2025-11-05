package com.marcenaria.sightcut.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.marcenaria.sightcut.model.Projeto;
import com.marcenaria.sightcut.model.ProjetoMaterial;
import com.marcenaria.sightcut.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class RelatorioService {

    private final ProjetoRepository projetoRepository;

    public RelatorioService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public String gerarPdfProjeto(Long projetoId) throws Exception {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado: " + projetoId));

        File dir = new File("relatorios");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String nomeArquivo = "Relatorio_Projeto_" + projeto.getCliente().getNome() + "_" +
                projeto.getTitulo().replaceAll("[^a-zA-Z0-9]", "_") + ".pdf";
        String caminho = "relatorios/" + nomeArquivo;

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(caminho));
        document.open();

        BaseColor corPrimaria = new BaseColor(41, 128, 185);
        BaseColor corSecundaria = new BaseColor(52, 73, 94);
        BaseColor corDestaque = new BaseColor(231, 76, 60);

        Font fontTituloPrincipal = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, corPrimaria);
        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
        Font fontSecao = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, corSecundaria);
        Font fontLabel = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, corSecundaria);
        Font fontValor = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        Font fontDestaque = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, corDestaque);

        Paragraph titulo = new Paragraph("Relatório de Projeto", fontTituloPrincipal);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(5);
        document.add(titulo);

        Paragraph subtitulo = new Paragraph("Powered by Sightcut", fontSubtitulo);
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(20);
        document.add(subtitulo);

        LineSeparator separator = new LineSeparator();
        separator.setLineColor(corPrimaria);
        separator.setLineWidth(2f);
        document.add(new Chunk(separator));
        document.add(Chunk.NEWLINE);

        adicionarSecao(document, "Dados do Projeto", fontSecao, corPrimaria);

        PdfPTable tableProjeto = new PdfPTable(2);
        tableProjeto.setWidthPercentage(100);
        tableProjeto.setWidths(new float[]{1f, 2f});
        tableProjeto.setSpacingBefore(10);
        tableProjeto.setSpacingAfter(15);

        adicionarLinha(tableProjeto, "Título:", projeto.getTitulo(), fontLabel, fontValor);
        adicionarLinha(tableProjeto, "Descrição:",
                projeto.getDescricao() != null ? projeto.getDescricao() : "Sem descrição",
                fontLabel, fontValor);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (projeto.getDataInicio() != null) {
            adicionarLinha(tableProjeto, "Data de Início:",
                    projeto.getDataInicio().format(formatter), fontLabel, fontValor);
        }
        if (projeto.getDataEntregaEstimada() != null) {
            adicionarLinha(tableProjeto, "Data de Entrega Estimada:",
                    projeto.getDataEntregaEstimada().format(formatter), fontLabel, fontValor);
        }

        document.add(tableProjeto);

        if (projeto.getCliente() != null) {
            adicionarSecao(document, "Dados do Cliente", fontSecao, corPrimaria);

            PdfPTable tableCliente = new PdfPTable(2);
            tableCliente.setWidthPercentage(100);
            tableCliente.setWidths(new float[]{1f, 2f});
            tableCliente.setSpacingBefore(10);
            tableCliente.setSpacingAfter(15);

            adicionarLinha(tableCliente, "Nome:", projeto.getCliente().getNome(), fontLabel, fontValor);

            if (projeto.getCliente().getEmail() != null) {
                adicionarLinha(tableCliente, "Email:", projeto.getCliente().getEmail(), fontLabel, fontValor);
            }
            if (projeto.getCliente().getTelefone() != null) {
                adicionarLinha(tableCliente, "Telefone:", projeto.getCliente().getTelefone(), fontLabel, fontValor);
            }
            if (projeto.getCliente().getCpf() != null) {
                adicionarLinha(tableCliente, "CPF:", projeto.getCliente().getCpf(), fontLabel, fontValor);
            }
            if (projeto.getCliente().getEndereco() != null) {
                adicionarLinha(tableCliente, "Endereço:", projeto.getCliente().getEndereco(), fontLabel, fontValor);
            }

            document.add(tableCliente);
        }

        if (projeto.getMateriais() != null && !projeto.getMateriais().isEmpty()) {
            adicionarSecao(document, "Materiais Utilizados", fontSecao, corPrimaria);

            PdfPTable tableMateriais = new PdfPTable(5);
            tableMateriais.setWidthPercentage(100);
            tableMateriais.setWidths(new float[]{3f, 1f, 1.2f, 1.5f, 1.5f});
            tableMateriais.setSpacingBefore(10);
            tableMateriais.setSpacingAfter(15);

            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            String[] headers = {"Material", "Qtd", "Unidade", "Preço Unit.", "Subtotal"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, fontHeader));
                cell.setBackgroundColor(corSecundaria);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(8);
                cell.setBorder(Rectangle.NO_BORDER);
                tableMateriais.addCell(cell);
            }

            Font fontTabela = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
            BaseColor corLinha1 = BaseColor.WHITE;
            BaseColor corLinha2 = new BaseColor(245, 245, 245);
            boolean isAlternate = false;

            for (ProjetoMaterial pm : projeto.getMateriais()) {
                BaseColor corLinha = isAlternate ? corLinha2 : corLinha1;

                adicionarCelulaTabela(tableMateriais, pm.getMaterial().getNome(),
                        fontTabela, Element.ALIGN_LEFT, corLinha);
                adicionarCelulaTabela(tableMateriais, String.format("%.2f", pm.getQuantidade()),
                        fontTabela, Element.ALIGN_CENTER, corLinha);
                adicionarCelulaTabela(tableMateriais, pm.getUnidade(),
                        fontTabela, Element.ALIGN_CENTER, corLinha);
                adicionarCelulaTabela(tableMateriais, String.format("R$ %.2f", pm.getPrecoUnitarioSnapshot()),
                        fontTabela, Element.ALIGN_RIGHT, corLinha);

                double subtotal = pm.getQuantidade() * pm.getPrecoUnitarioSnapshot();
                adicionarCelulaTabela(tableMateriais, String.format("R$ %.2f", subtotal),
                        fontTabela, Element.ALIGN_RIGHT, corLinha);

                isAlternate = !isAlternate;
            }

            document.add(tableMateriais);
        }

        PdfPTable tableCusto = new PdfPTable(1);
        tableCusto.setWidthPercentage(100);
        tableCusto.setSpacingBefore(10);
        tableCusto.setSpacingAfter(20);

        PdfPCell cellCusto = new PdfPCell(new Phrase(
                String.format("CUSTO TOTAL DO PROJETO: R$ %.2f", projeto.getCustoTotal()),
                fontDestaque
        ));
        cellCusto.setBackgroundColor(new BaseColor(255, 248, 220));
        cellCusto.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellCusto.setPadding(15);
        cellCusto.setBorder(Rectangle.BOX);
        cellCusto.setBorderColor(corDestaque);
        cellCusto.setBorderWidth(2f);
        tableCusto.addCell(cellCusto);

        document.add(tableCusto);

        LineSeparator separatorRodape = new LineSeparator();
        separatorRodape.setLineColor(corPrimaria);
        separatorRodape.setLineWidth(1f);
        document.add(new Chunk(separatorRodape));

        Paragraph rodape = new Paragraph("Relatório gerado pelo sistema SightCut",
                new Font(Font.FontFamily.HELVETICA, 9, Font.ITALIC, BaseColor.GRAY));
        rodape.setAlignment(Element.ALIGN_CENTER);
        rodape.setSpacingBefore(10);
        document.add(rodape);

        document.close();
        return caminho;
    }

    private void adicionarSecao(Document doc, String titulo, Font font, BaseColor cor) throws DocumentException {
        Paragraph secao = new Paragraph(titulo, font);
        secao.setSpacingBefore(5);
        secao.setSpacingAfter(5);
        doc.add(secao);
    }

    private void adicionarLinha(PdfPTable table, String label, String valor, Font fontLabel, Font fontValor) {
        PdfPCell cellLabel = new PdfPCell(new Phrase(label, fontLabel));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        cellLabel.setPadding(5);
        cellLabel.setBackgroundColor(new BaseColor(245, 245, 245));
        table.addCell(cellLabel);

        PdfPCell cellValor = new PdfPCell(new Phrase(valor, fontValor));
        cellValor.setBorder(Rectangle.NO_BORDER);
        cellValor.setPadding(5);
        table.addCell(cellValor);
    }

    private void adicionarCelulaTabela(PdfPTable table, String texto, Font font, int align, BaseColor cor) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(align);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setBackgroundColor(cor);
        table.addCell(cell);
    }
}