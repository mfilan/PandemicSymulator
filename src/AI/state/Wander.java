package AI.state;

import AI.AITransition;
import controller.PersonController;
import core.Position;
import entity.MovingEntity;
import entity.Person;
import map.Pathfinder;
import map.SymulatorMap;

import java.util.ArrayList;
import java.util.List;

public class Wander extends AIState{

    private List<Position> path;
    private Position target;
    private String floorType;

    public Wander(String floorType) {
        super();
        path = new ArrayList<>();
        this.floorType = floorType;
    }

    @Override
    protected AITransition initializeTransition() {
        return new AITransition("stand",((currentCharacter -> arrived(currentCharacter))));
    }

    @Override
    public void update(SymulatorMap symulatorMap, MovingEntity currentCharacter) {
        if(target ==null){
            Position targetPosition = symulatorMap.getRandomShopPosition();
            if (((Person) currentCharacter).getCurrentProducts().size() == 0 && floorType.equals("road")){
                targetPosition = symulatorMap.getRandomWholeSalePosition();
            }
            List<Position> path = Pathfinder.findPath(currentCharacter.getPosition(),targetPosition, symulatorMap,floorType);
            if(!path.isEmpty()){
                target = path.get(path.size()-1);
                this.path.addAll(path);
            }
        }
        PersonController controller = (PersonController)  currentCharacter.getController();


        if(arrived(currentCharacter)){
            controller.stop();
        }
        if(!path.isEmpty() && currentCharacter.getPosition().isInRangeOf(path.get(0))){
            path.remove(0);
        }
        if(!path.isEmpty()){
//
            controller.moveToTarget(path.get(0),currentCharacter.getPosition());
        }

    }

    public void setPath(List<Position> path) {
        this.path = path;
    }

    private boolean arrived(MovingEntity currentCharacter){
        return target != null &&currentCharacter.getPosition().isInRangeOf(target);
    }


}
