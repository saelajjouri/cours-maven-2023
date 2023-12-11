package fr.imt.cours.storage.container;

public class Cup extends Container{

    public Cup(double capacity){
        super(capacity);
    }

    public Cup(Container container){
        super(container);
    }
}
