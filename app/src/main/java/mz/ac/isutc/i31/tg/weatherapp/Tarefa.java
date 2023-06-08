package mz.ac.isutc.i31.tg.weatherapp;

public class Tarefa {
    private String descricao;
    private String temperatura;

    public Tarefa(String descricao, String temperatura) {
        this.descricao = descricao;
        this.temperatura = temperatura;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "descricao='" + descricao + '\'' +
                ", temperatura='" + temperatura + '\'' +
                '}';
    }
}
