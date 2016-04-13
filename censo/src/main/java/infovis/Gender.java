package infovis;
import lombok.Data;

@Data
public class Gender {
    private int femaleCount;
    private int maleCount;
    
    public Gender(int femaleCount, int maleCount) {
		this.femaleCount = femaleCount;
		this.maleCount = maleCount;
	}
}
