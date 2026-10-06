package br.edu.fatecpg.view;

import br.edu.fatecpg.model.Carrinho;
import br.edu.fatecpg.model.RespostaCarrinhos;
import br.edu.fatecpg.service.ConsomeAPI;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        try {

            ConsomeAPI api = new ConsomeAPI();
            RespostaCarrinhos resposta = api.buscarCarrinhos();

            // 1. Carrinhos com total acima de US$ 1000
            System.out.println("=== CARRINHOS ACIMA DE US$ 1000 ===");

            resposta.getCarts()
                    .stream()
                    .filter(c -> c.getTotal() > 1000)
                    .forEach(c -> System.out.println(c));

            // 2. Produtos com desconto acima de 15%
            System.out.println("\n=== PRODUTOS COM DESCONTO ACIMA DE 15% ===");

            resposta.getCarts()
                    .stream()
                    .flatMap(c -> c.getProducts().stream())
                    .filter(p -> p.getDiscountPercentage() > 15)
                    .map(p -> p.getTitle())
                    .forEach(titulo -> System.out.println(titulo));

            // 3. Carrinhos ordenados pela maior economia
            System.out.println("\n=== CARRINHOS ORDENADOS POR ECONOMIA ===");

            resposta.getCarts()
                    .stream()
                    .sorted((c1, c2) -> Double.compare(
                            c2.getEconomia(),
                            c1.getEconomia()
                    ))
                    .forEach(c -> System.out.println(c));

            // 4. Soma dos discountedTotal
            double soma = resposta.getCarts()
                    .stream()
                    .map(c -> c.getDiscountedTotal())
                    .reduce(0.0, (acumulador, valor) -> acumulador + valor);

            System.out.println("\n=== SOMA DOS DISCOUNTED TOTAL ===");
            System.out.println("US$ " + soma);

            // 5. Agrupamento por quantidade de produtos
            System.out.println("\n=== CARRINHOS POR QUANTIDADE DE PRODUTOS ===");

            Map<Integer, Long> grupos = resposta.getCarts()
                    .stream()
                    .collect(Collectors.groupingBy(
                            c -> c.getTotalProducts(),
                            Collectors.counting()
                    ));

            grupos.forEach((quantidadeProdutos, quantidadeCarrinhos) ->
                    System.out.println(
                            quantidadeProdutos + " produtos -> "
                                    + quantidadeCarrinhos + " carrinho(s)"
                    )
            );

            // 6. EXTRA: carrinho de maior valor
            System.out.println("\n=== CARRINHO DE MAIOR VALOR ===");

            Optional<Carrinho> maior = resposta.getCarts()
                    .stream()
                    .max((c1, c2) -> Double.compare(
                            c1.getTotal(),
                            c2.getTotal()
                    ));

            maior.ifPresent(c -> System.out.println(
                    "Carrinho #" + c.getId()
                            + " | Total: US$ " + c.getTotal()
            ));

            // 7. EXTRA: formatação com lambda
            System.out.println("\n=== CARRINHOS FORMATADOS ===");

            Function<Carrinho, String> formatarCarrinho = c ->
                    "Carrinho #" + c.getId()
                            + " | Usuário: " + c.getUserId()
                            + " | Itens: " + c.getTotalQuantity()
                            + " | Total: US$ " + c.getDiscountedTotal();

            resposta.getCarts()
                    .stream()
                    .map(formatarCarrinho)
                    .forEach(System.out::println);

        } catch (IOException e) {

            System.out.println(
                    "Erro de conexão ou HTTP: " + e.getMessage()
            );

        } catch (InterruptedException e) {

            System.out.println(
                    "A requisição foi interrompida."
            );

        } catch (JsonSyntaxException e) {

            System.out.println(
                    "Erro: JSON malformado."
            );
        }
    }
}