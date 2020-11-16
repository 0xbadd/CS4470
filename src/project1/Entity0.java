package project1;

import java.util.Arrays;

public class Entity0 extends Entity
{
    // Perform any necessary initialization in the constructor
    public Entity0()
    {
        final int NODE_NUM = 1;
        final int[] NEIGHBORS = {0, 1};
        int[] neighbor_costs = {0, 1, 3, 7};

        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            Arrays.fill(distanceTable[i], 999);
        }
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            distanceTable[NODE_NUM][i] = neighbor_costs[i];
        }
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
