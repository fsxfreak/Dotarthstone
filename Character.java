public abstract class Character 
{
	protected String name;
	protected int health;
	protected int maxHealth;
	protected int damage;

	public Character(String name, int health, int damage)
	{
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.damage = damage;
	}

	public void attack(Character target)
	{
		target.hurt(damage);
		hurt(target.getDamage());
	}

	public void hurt(int amount) 
	{ 
		health -= amount; 
		if (health <= 0)
			Board.remove(this);
	}

	public void heal(int amount) 
	{ 
		health += amount;
		if (health > maxHealth)
			health = maxHealth;
	}

	public int getHealth() { return health; }
	public int getDamage() { return damage; }
}