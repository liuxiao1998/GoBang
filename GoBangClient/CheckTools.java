import java.util.ArrayList;
public class CheckTools{
    private  static final short BLACK=1;
    private  static final short WHITE=-1;
    private  static final short EMPTY=0;

    private static short checkLineFinished(ArrayList<Short> line)
    {
        //System.out.println(line);
        int countBlack=0;
        int countWhite=0;
        for(int i=0;i<line.size();i++)
        {
            if(line.get(i)==BLACK){
                countWhite=0;
                countBlack+=1;
            }

            else if(line.get(i)==WHITE){
                countBlack=0;
                countWhite+=1;
            }
            else if(line.get(i)==EMPTY){
                countBlack=0;
                countWhite=0;
            }
            if(countBlack==5){
                return 1;
            }
            else if(countWhite==5){
                return -1;
            }
        }
        return 0;
    }


    public static short checkMap(short[][] grid){
        var aLine=new ArrayList<Short>();
        short res=0;

        //横向检查(___)
        for(int i=0;i<grid.length;i++){
            aLine.clear();
            for(int j=0;j<grid[i].length;j++){
                aLine.add((Short)grid[i][j]);
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        //竖直方向检查(|||)
         for(int i=0;i<grid[0].length;i++){
            aLine.clear();
            for(int j=0;j<grid.length;j++){
                aLine.add((Short)grid[j][i]);
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        //右斜检查(\\\)
        for(int i=0;i<grid.length-4;i++){
            aLine.clear();
            int x=i;
            int y=0;
            while(x<grid.length){
                aLine.add((Short)grid[x][y]);
                x+=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        for(int j=1;j<grid[0].length-4;j++){

            aLine.clear();
            int x=0;
            int y=j;
            while(y<grid[0].length){
                aLine.add((Short)grid[x][y]);
                x+=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }
        //左斜检查  (///)
        for(int i=4;i<grid.length;i++){
            aLine.clear();
            int x=i;
            int y=0;
            while(x>=0){
                aLine.add((Short)grid[x][y]);
                x-=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }

        for(int i=1;i<grid[0].length-4;i++){
            aLine.clear();
            int x=grid.length-1;
            int y=i;
            while(y<grid[0].length){
                aLine.add((Short)grid[x][y]);
                x-=1;
                y+=1;
            }
            res=checkLineFinished(aLine);
            if(res!=0){
                return res; 
            }
        }
        return 0;
    }

}