import java.util.ArrayList;

public abstract class Card extends Character
{
	protected int manaCost;
	protected String name;
	protected ArrayList<Type> effects;

	public Card(String name, int health, int damage, int manaCost)
	{
		super(name, health, damage);
		this.manaCost = manaCost;
	}

	protected void addEffect(Type effect) { effects.add(effect); }
	public String getName() { return name; }
}