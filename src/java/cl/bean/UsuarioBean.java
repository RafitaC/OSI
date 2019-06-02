package cl.bean;

import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import cl.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rafael Chica
 */
@ManagedBean(name = "user")
@SessionScoped
public class UsuarioBean implements Serializable{


    private String nombre;
    
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    
    private HttpSession sesion;

    public UsuarioBean() {
        
        sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        
    }
    

    public String loguear() {
        usuarios.add(new Usuario(nombre));
        
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            for (Usuario user : usuarios) {
                if (user.getNombre().equals(nombre)) {
                    
                    sesion.setAttribute("usuario", user);
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/faces/menu.xhtml");
                }else{
                  //  sesion.setAttribute("usuario", user);
                  //  FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/faces/menu.xhtml");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No estas registrado"));
        return "index";
    
    }
    
    public String cerrarSesion(){
        try {
            sesion.invalidate();
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path);
        } catch (IOException e) {
        }
        return "index";
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public HttpSession getSesion() {
        return sesion;
    }

    public void setSesion(HttpSession sesion) {
        this.sesion = sesion;
    }

}