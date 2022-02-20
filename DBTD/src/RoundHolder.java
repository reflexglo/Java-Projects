
import java.util.ArrayList;

public class RoundHolder {
    EnemyHolder enemyHolder = new EnemyHolder();
    public ArrayList<Enemy> round(int round){
        ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
        if(round == 1){
            int numEnemies = 1;
            for(int i = 0;i<10;i++){
                enemyList.add(enemyHolder.pilaf());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
        }
        if(round == 2){
            int numEnemies = 1;
            for(int i = 0;i<15;i++){
                enemyList.add(enemyHolder.pilaf());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
            for(int i = 0;i<5;i++){
                enemyList.add(enemyHolder.commander());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
        }
        if(round == 3){
            int numEnemies = 1;
            for(int i = 0;i<5;i++){
                enemyList.add(enemyHolder.pilaf());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
            for(int i = 0;i<7;i++){
                enemyList.add(enemyHolder.commander());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
            for(int i = 0;i<4;i++){
                enemyList.add(enemyHolder.takeo());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
        }
        if(round == 4){
            int numEnemies = 1;
            for(int i = 0;i<6;i++){
                enemyList.add(enemyHolder.takeo());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
            for(int i = 0;i<4;i++){
                enemyList.add(enemyHolder.tien());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
            for(int i = 0;i<6;i++){
                enemyList.add(enemyHolder.takeo());
                enemyList.get(numEnemies-1).setX(enemyList.get(numEnemies-1).getX()-140*numEnemies);
                numEnemies++;
            }
        }
        return enemyList;
    }
}
