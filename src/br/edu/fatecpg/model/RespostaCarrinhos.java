package br.edu.fatecpg.model;
import java.util.List;


public class RespostaCarrinhos {

    private List<Carrinho> carts ;
    private int total;
    private int skip;
    private int limit;


    public RespostaCarrinhos(){}
    public RespostaCarrinhos(List<Carrinho> carts, int total, int skip, int limit) {
        this.carts = carts;
        this.total = total;
        this.skip = skip;
        this.limit = limit;
    }

    public List<Carrinho> getCarts() {
        return carts;
    }

    public void setCarts(List<Carrinho> carts) {
        this.carts = carts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "RespostaCarrinhos{" +
                "carts=" + carts +
                ", total=" + total +
                ", skip=" + skip +
                ", limit=" + limit +
                '}';
    }
}
