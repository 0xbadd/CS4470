package project1;

import java.util.Arrays;

public class Entity0 extends Entity
{
    final int NODE_NUM = 0;
    final int[] NEIGHBORS = {1, 2, 3};

    // Perform any necessary initialization in the constructor
    public Entity0()
    {
        int[] neighbor_costs = {0, 1, 3, 7};

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            Arrays.fill(distanceTable[i], 999);
        }
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            distanceTable[NODE_NUM][i] = neighbor_costs[i];
        }

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("Initial distance table for Node " + NODE_NUM);
        System.out.println("--------------------------------");
        printDT();

        for (int neighbor : NEIGHBORS) {
            Packet dtPacket = new Packet(NODE_NUM, neighbor, neighbor_costs);
            NetworkSimulator.toLayer2(dtPacket);
        }
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        int[] min_distance = new int[NetworkSimulator.NUMENTITIES];
        boolean table_changed = false;

        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("update() for Node " + NODE_NUM);
        System.out.println("--------------------------------");
        System.out.println("Packet Sender: Node " + p.getSource());

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            int current_cost = distanceTable[NODE_NUM][i];
            int source_cost = distanceTable[NODE_NUM][p.getSource()];
            int packet_cost = p.getMincost(i);

            if (source_cost + packet_cost < current_cost) {
                min_distance[i] = source_cost + packet_cost;
                distanceTable[NODE_NUM][i] = min_distance[i];
                table_changed = true;
            } else {
                min_distance[i] = current_cost;
            }

            distanceTable[p.getSource()][i] = packet_cost;
        }

        if (table_changed) {
            System.out.println("*Distance table changed*");
            System.out.println("Minimum distances sent: " + Arrays.toString(min_distance));

            for (int neighbor : NEIGHBORS) {
                Packet dtPacket = new Packet(NODE_NUM, neighbor, min_distance);
                NetworkSimulator.toLayer2(dtPacket);
            }
        } else {
            System.out.println("Distance table not changed.");
        }

        printDT();
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
