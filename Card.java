import java.util.ArrayList;

public abstract class Card extends Character
{
	protected int manaCost;
	protected ArrayList<Effect> effects;

	public Card(String name, int health, int damage, int manaCost)
	{
		super(name, health, damage);
		this.manaCost = manaCost;
	}

	public void addEffect(Effect effect) { effects.add(effect); }

}