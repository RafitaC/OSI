package cl.bean;

import cl.model.Pregunta;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Rafael Chica
 */
@ManagedBean(name = "preguntar")
@SessionScoped
public class JuegoBean {

    /**
     * Creates a new instance of JuegoBean
     */
    private int numero;
    private String pregunta;
    private String alternativa1;
    private String alternativa2;
    private String alternativa3;
    private String alternativa4;
    private String respuesta;
    private String respuestajugador;
    private ArrayList<Pregunta> preguntas = new ArrayList<>();
    private ArrayList<Pregunta> lista_eliminado = new ArrayList<>();
    private ArrayList<Pregunta> ramdon = new ArrayList<>();
    private ArrayList lista = new ArrayList<>();
    private ArrayList<Pregunta> filtro = new ArrayList<>();
    private boolean validar;

    /**
     * Creates a new instance of JuegoBean
     */
    public JuegoBean() {
        preguntas.add(new Pregunta(1, "¿Cuál es el objetivo del modelo OSI?", "Hacer nuevos modelos de conexión", "Planear nuevas conexiones. ", "Estandarizar la conexión de dispositivos de Sistemas Abiertos", "Ninguna de las anteriores", "Estandarizar la conexión de dispositivos de Sistemas Abiertos"));
        preguntas.add(new Pregunta(2, "¿En que capa del modelo OSI está Nivel de Red?", "Capa 1", "Capa 2", "Capa 3", "Capa 4", "Capa 3"));
        preguntas.add(new Pregunta(3, "Son algunos elementos de la capa física:", "Cableado vertical, Usuarios y acceso a la red", "Firewall, topología de red", "Router, Hub, direcciones MAC", "Cableado estructurado, fibra óptica y radio frecuencia de señal", "Cableado estructurado, fibra óptica y radio frecuencia de señal"));
        preguntas.add(new Pregunta(4, "Es un equipo que trabajan en la capa 2:", "Fibra Optica", "Switch", "Todas las anteriores", "Rj45", "Switch"));
        preguntas.add(new Pregunta(5, "La capa 3 del modelo OSI se encarga de:", "Conectividad y medios de comunicación", "Enrutar y medios de comunicación", "Direccionamiento y enrutamiento", "Todas las anteriores", "Direccionamiento y enrutamiento"));
        preguntas.add(new Pregunta(6, "Un Router en que capa del modelo OSI se encuentra ubicado:", "Capa 1: Física", "Capa 4: Transporte", "Capa 2: Enlace de datos", "Capa 3: Red", "Capa 3: Red"));
        preguntas.add(new Pregunta(7, "Que se destaca de la capa 4: Transporte ", "Confiabilidad del transporte", "Mantiene y termina adecuadamente los circuitos virtuales", "A y B son correctas", "Ninguna de las anteriores", "A y B son correctas"));
        

        ramdon.add(new Pregunta(1, "¿Cuál es el objetivo del modelo OSI?", "Hacer nuevos modelos de conexión", "Planear nuevas conexiones. ", "Estandarizar la conexión de dispositivos de Sistemas Abiertos", "Ninguna de las anteriores", "Estandarizar la conexión de dispositivos de Sistemas Abiertos"));
    }

    public String guardar() {

        boolean existe = false;
        for (Pregunta p : preguntas) {
            if (p.getNumero() == numero) {
                existe = true;
            }
        }
        if (existe) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Numero existente!!"));
            numero = 0;
        } else {
            preguntas.add(new Pregunta(numero, pregunta, alternativa1, alternativa2, alternativa3, alternativa4, respuesta));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pregunta creada!!"));

            numero = 0;
            pregunta = "";
            alternativa1 = "";
            alternativa2 = "";
            alternativa3 = "";
            alternativa4 = "";
            respuesta = "";
        }

        return "gestionPregunta";
    }

    public String eliminar() {
        for (Pregunta p : preguntas) {
            if (p.isSelected()) {
                lista_eliminado.add(p);
            }

        }
        if (!lista_eliminado.isEmpty()) {
            preguntas.removeAll(lista_eliminado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pregunta Eliminada"));

        }
        return "index";
    }

    public void hacerValid() {
        for (Pregunta p : preguntas) {
            if (p.getNumero() == numero) {
                validar = true;
                break;
            }
        }
        validar = false;

    }

    int dis(int num) {
        int cont = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (num == (int) lista.get(i)) {
                cont++;
            }
        }
        return cont;
    }

    public String jugar() throws IOException {
        int num = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            num++;
        }
        int cont = 0;
        Random r = new Random();
        int numer = 0;
        int aux = 0;
        boolean si = true;

        while (si) {
            aux = 1 + r.nextInt(num);
            lista.add(aux);
            if (dis(aux) == 1) {
                si = false;
            }
        }

        numer = aux;
        boolean esta = false;

        if (!ramdon.isEmpty()) {
            for (Pregunta p : ramdon) {
                if (p.getRespuesta().equalsIgnoreCase(respuestajugador)) {
                    esta = true;
                } else {
                    esta = false;
                }
            }
            if (esta == true) {
                for (int i = 0; i < filtro.size(); i++) {
                    cont++;
                }
                if (cont == 18) {
                    cont = 0;
                    filtro.clear();
                    FacesContext.getCurrentInstance().getExternalContext().redirect("win.xhtml");
                    filtro.clear();
                    lista.clear();
                    for (int i = 0; i < ramdon.size(); i++) {
                        if (i == 1) {
                            ramdon.clear();
                        }
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("respuesta correcta"));
                for (int i = 0; i < ramdon.size(); i++) {
                    if (i == 1) {
                        ramdon.clear();
                    }
                    for (Pregunta p : preguntas) {
                        if (numer == p.getNumero()) {
                            ramdon.add(p);
                            filtro.add(p);
                        }
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("respuesta incorrecta"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("loss.xhtml");
                cont = 0;
                filtro.clear();
                lista.clear();
                for (int i = 0; i < ramdon.size(); i++) {
                    if (i == 1) {
                        ramdon.clear();
                    }
                }
            }
        }
        return "juego";
    }

    public String nuevoJuego() {
        return null;
    }

    public String terminarJuego() {
        return null;
    }

    public void actualizar(RowEditEvent event) {
        //obtenemos de la tabla el producto a editar
        Pregunta p = (Pregunta) event.getObject();
        p.setPregunta(pregunta);
        p.setAlternativa1(alternativa1);
        p.setAlternativa1(alternativa1);
        p.setAlternativa2(alternativa2);
        p.setAlternativa3(alternativa3);
        p.setAlternativa4(alternativa4);
        p.setRespuesta(respuesta);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(" Pregunta Editada!!"));

    }

    public void cancelar(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cancelar"));
    }

//---------------------------------------------------------------------------------------------------------------
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getAlternativa1() {
        return alternativa1;
    }

    public void setAlternativa1(String alternativa1) {
        this.alternativa1 = alternativa1;
    }

    public String getAlternativa2() {
        return alternativa2;
    }

    public void setAlternativa2(String alternativa2) {
        this.alternativa2 = alternativa2;
    }

    public String getAlternativa3() {
        return alternativa3;
    }

    public void setAlternativa3(String alternativa3) {
        this.alternativa3 = alternativa3;
    }

    public String getAlternativa4() {
        return alternativa4;
    }

    public void setAlternativa4(String alternativa4) {
        this.alternativa4 = alternativa4;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuestajugador() {
        return respuestajugador;
    }

    public void setRespuestajugador(String respuestajugador) {
        this.respuestajugador = respuestajugador;
    }

    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public ArrayList<Pregunta> getLista_eliminado() {
        return lista_eliminado;
    }

    public boolean isValidar() {
        return validar;
    }

    public void setValidar(boolean validar) {
        this.validar = validar;
    }

    public void setLista_eliminado(ArrayList<Pregunta> lista_eliminado) {
        this.lista_eliminado = lista_eliminado;
    }

    public ArrayList<Pregunta> getRamdon() {
        return ramdon;
    }

    public void setRamdon(ArrayList<Pregunta> ramdon) {
        this.ramdon = ramdon;
    }

    public ArrayList getLista() {
        return lista;
    }

    public void setLista(ArrayList lista) {
        this.lista = lista;
    }

    public ArrayList<Pregunta> getFiltro() {
        return filtro;
    }

    public void setFiltro(ArrayList<Pregunta> filtro) {
        this.filtro = filtro;
    }

}
