package model;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;
import sim.field.grid.SparseGrid2D;
import sim.util.Bag;
import sim.util.Int2D;

public class Field extends SimState
{
	public SparseGrid2D field = new SparseGrid2D(Constants.gridSize, Constants.gridSize);
	
	public Field(long seed) 
	{
		super(seed);
	}
	
	public Spot getFreeSpot()
	{
		Spot spot = new Spot();
		
    	while(field.getObjectsAtLocation(spot.getX(), spot.getY()) != null)
    	{
    		spot.setX((int)(Math.random() * Constants.gridSize));
    		spot.setY((int)(Math.random() * Constants.gridSize));
    	}
    	
    	return spot;
	}
	
	public void start() 
	{
		Spot spot;
		
		System.out.println("Simulation started !");
		super.start();
	    field.clear();
	    
	    for(int i = 0 ; i < Constants.nbInsects ; i++)
	    {	
	    	spot = getFreeSpot();
	    	
	    	int[] randomCapacities = Insect.randomCapacities();
	    	
	    	Insect insect = new Insect(i, spot, randomCapacities[0], randomCapacities[1], randomCapacities[2]);
	    	field.setObjectLocation(insect, spot.getX(), spot.getY());
	    	
	    	Stoppable stoppable = schedule.scheduleRepeating(insect);
	    	insect.stoppable = stoppable;
	    }
	    
	    for(int i = 0 ; i < Constants.nbFood ; i++)
	    {
	    	spot = getFreeSpot();
	    	Food food = new Food(i, spot);
	    	field.setObjectLocation(food, spot.getX(), spot.getY());
	    	
	    	Stoppable stoppable = schedule.scheduleRepeating(food);
	    	food.stoppable = stoppable;
	    }
	}
}