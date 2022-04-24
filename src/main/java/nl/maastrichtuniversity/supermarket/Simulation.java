package nl.maastrichtuniversity.supermarket;

import nl.maastrichtuniversity.supermarket.models.*;

public class Simulation {
    public static void main(String[] args) {
        EventList list = new EventList();
        Queue queue = new Queue();
        Source source = new Source(queue, list, "Source 1");
        Sink sink = new Sink("Sink 1");
        Machine machine = new Machine(queue, sink, list, "Machine 1");

        list.start(2000);
    }
}
