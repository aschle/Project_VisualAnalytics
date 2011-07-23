package squarification;

import java.util.ArrayList;

public class Squarification {
	
	int dimX = 600 ;
	int dimY = 400;
	
	private void squarify(ArrayList<Integer> children, ArrayList<Integer> row, int width){
		
		if (children.size() < 1){
			return;
		}
		
		int first = children.get(0);
		
		ArrayList<Integer> newRow = (ArrayList<Integer>) row.clone();
		newRow.add(first);
		
		if(worst(row,width)>= worst(newRow, width)){
			ArrayList<Integer> newChildren = new ArrayList<Integer>();
			for (int i = 1; i< children.size(); i++){
				newChildren.add(children.get(i));
			}
			squarify(children, newRow, width);
			
		}
		else {
			layoutRow(row);
			squarify(children, new ArrayList<Integer>(), width());
		}
		
	}
	
	public int width(){
		if (dimX <= dimY)
			return dimX;
			else
				return dimY;
	}
	
	public float worst(ArrayList<Integer> areas, int width){
		return 0;
	}
	
	public void layoutRow(ArrayList<Integer >row){
		
	}

	
	public static void main(String[] args) {
	}
}
