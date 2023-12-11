package fr.imt.cours.machine.component;

public class SteamPipe {
    private boolean isOn;

    /**
     * Constructeur de la buse de vapeur pour machine à expresso
     */
    public SteamPipe() {
        this.isOn = false;
    }

    /**
     * Getter pour savoir si la buse de vapeur est en fonctionnement
     * @return Booléen donnant le fonctionnement ou non de la buse de vapeur
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * Setter automatique pour allumer la buse de vapeur
     */
    public void setOn() {
        isOn = true;
    }

    /**
     * Setter automatique pour éteindre la buse de vapeur
     */
    public void setOff() {
        isOn = false;
    }
}
