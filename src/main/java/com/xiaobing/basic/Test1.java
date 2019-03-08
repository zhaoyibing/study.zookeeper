package com.xiaobing.basic;

import java.util.HashSet;
import java.util.Set;

/**
 * equals 和 hashcode判断
 * 1，相等（相同）的对象必须具有相等的哈希码
 * 2，不同对象的hashCode有可能相同
 * @author e_gaoyunc
 *
 */
public class Test1 {

	public static void main(String[] args) {

		Person p1 = new Person(10,"张三");
		Person p2 = new Person(10,"张三");
		Person p3 = new Person(10,"张三1");
		
		System.out.println(p1.equals(p2));
		System.out.println(p1.equals(p3));
		
		System.out.println(p1.hashCode());
		System.out.println(p2.hashCode());
		System.out.println(p3.hashCode());
		
		Set<Person> personSet = new HashSet<Person>();
		personSet.add(p1);
		personSet.add(p2);
		personSet.add(p3);
		
		System.out.println("person集合的size=" + personSet.size());
		for (Person person : personSet) {
			System.out.println(person);
		}
		
	}
	



}

class Person {
	private int age;
	private String name;

	public Person(int age, String name) {
		super();
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(!(obj instanceof Person))
			return false;
		Person other = (Person) obj;
		return this.age == other.age && this.name.equals(other.getName());
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode() + this.age;
	}

}



