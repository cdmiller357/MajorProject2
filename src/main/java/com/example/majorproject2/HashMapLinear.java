package com.example.majorproject2;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Christopher Miller  Computer Science II 2022 Spring 13W ONL2
 * Book: Liang -- Introduction to Java Programming and Data Structures 12ed
 */
/* Q 27.1 (HashMapLinear) - Use MyHashMap class that is provided in the book to create a new class HashMapLinear and
 * convert the code from using separate chaining to linear open addressing. Will require removing LinkedLists and
 * associated foreach loops and change to for loops that iterate through the whole list in some cases or to a while loop
 * that starts at the beginning of cluster until it reaches null in other cases. Each element in the array will be a single
 * Entry Object now. Use TestMyHashMap to verify that HashMapLinear performs the same as MyHashMap.
 */

public class HashMapLinear<K, V>  {
  // Define the default hash table size. Must be a power of 2
  private static int DEFAULT_INITIAL_CAPACITY = 4;
  
  // Define the maximum hash table size. 1 << 30 is same as 2^30
  private static int MAXIMUM_CAPACITY = 1 << 30; 
  
  // Current hash table capacity. Capacity is a power of 2
  private int capacity;
  
  // Define default load factor
  private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f; 

  // Specify a load factor used in the hash table
  private float loadFactorThreshold; 
     
  // The number of entries in the map
  private int size = 0; 
  
  // Hash table is an array with each cell that is a linked list
  Entry<K,V>[] table; //Q27.1 change to array of Entry objects

  /** Construct a map with the default capacity and load factor */
  public HashMapLinear() {  
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a map with the specified initial capacity and 
   * default load factor */
  public HashMapLinear(int initialCapacity) { 
    this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a map with the specified initial capacity 
   * and load factor */
  public HashMapLinear(int initialCapacity, float loadFactorThreshold) { 
    if (initialCapacity > MAXIMUM_CAPACITY)
      this.capacity = MAXIMUM_CAPACITY;
    else
      this.capacity = trimToPowerOf2(initialCapacity);
    
    this.loadFactorThreshold = loadFactorThreshold;    
    table = new Entry[capacity];
  }
  
   /** Remove all of the entries from this map */ 
  public void clear() {
    size = 0;
    removeEntries();
  }

   /** Return true if the specified key is in the map */
  public boolean containsKey(K key) {    
    if (get(key) != null)
      return true;
    else
      return false;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Increment through list by 1 and return value is found
   */
   /** Return true if this map contains the value */ 
  public boolean containsValue(V value) {
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null && table[i].value.equals(value)) {
        return true;
      }
    }    
    return false;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Increment through list by 1 add Entry objects to set
   */
   /** Return a set of entries in the map */
  public Set<Entry<K,V>> entrySet() {
    Set<Entry<K, V>> set =  new HashSet<>();
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null)
        set.add(table[i]);
      }
    return set;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Find index and increment through list by 1 until null looking for key
   */
   /** Return the value that matches the specified key */
  public V get(K key) {
    int index = hash(key.hashCode());
    while (table[index] != null) { //linear
        if (table[index].key.equals(key))
          return table[index].value;
        index++;
    }
    return null;
  }
  
   /** Return true if this map contains no entries */
  public boolean isEmpty() {
    return size == 0;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Increment through list by 1 add keys to set
   */
   /** Return a set consisting of the keys in this map */
  public Set<K> keySet() {
    Set<K> set = new HashSet<>();    
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
          set.add(table[i].key);
      }
    }    
    return set;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  For existing     key, Increment through cluster by 1 to find index of existing key
   *  For non-existing key, Increment through cluster by 1 to find first open index of cluster
   */
   /** Add an entry (key, value) into the map */
  public V put(K key, V value) {
    int index = hash(key.hashCode());
    if (get(key) != null) { // The key is already in the map
      while (table[index] != null) { //linear
        if (table[index].key == key)
          break;
        index++;
      }
      V oldValue = get(key);
      // Replace old value with new value
      table[index].value = value;
      // Return the old value for the key
      return oldValue;
    }
  
    // Check load factor
    if (size >= capacity * loadFactorThreshold) {
      if (capacity == MAXIMUM_CAPACITY)
        throw new RuntimeException("Exceeding maximum capacity");
      rehash();
    }

    while (table[index] != null)  //linear //??&& table[i].key != null??
      index++;
    // Add a new entry (key, value) to hashTable[index]
    table[index] = new Entry<K, V>(key, value);
    size++; // Increase size
    return value;  
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Increment through cluster by 1 to find index of existing key
   */
   /** Remove the entries for the specified key */
  public void remove(K key) {
    int index = hash(key.hashCode());
    // Remove the first entry that matches the key from a bucket
    while (table[index] != null) {
      if (table[index].key.equals(key)) {
        table[index] = null;
        break; // Remove just one entry that matches the key
      }
      index++;
    }
  }
  
   /** Return the number of entries in this map */
  public int size() {
    return size;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList
   *  Increment through list by 1 add values to set
   */
   /** Return a set consisting of the values in this map */
  public Set<V> values() {
    Set<V> set = new HashSet<>();    
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
          set.add(table[i].value);
      }
    }    
    return set;
  }
  
  /** Hash function */
  private int hash(int hashCode) {
    return supplementalHash(hashCode) & (capacity - 1);
  }
  
  /** Ensure the hashing is evenly distributed */
  private static int supplementalHash(int h) {
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
  }

  /** Return a power of 2 for initialCapacity */
  private int trimToPowerOf2(int initialCapacity) {
    int capacity = 1;
    while (capacity < initialCapacity) {
      capacity <<= 1;
    }
    
    return capacity;
  }

  /*  Q27.1 Changed from Separate chaining to linear open addressing
   *  Remove LinkedList clear() method call
   */
  /** Remove all entries from each bucket */
  private void removeEntries() {
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null)
        table[i] = null;
    }
  }
  
  /** Rehash the map */
  private void rehash() {
    Set<Entry<K, V>> set = entrySet(); // Get entries
    capacity <<= 1; // Double capacity    
    table = new Entry[capacity]; // Create a new hash table
    size = 0; // Reset size to 0
    
    for (Entry<K, V> entry: set) {
      put(entry.getKey(), entry.getValue()); // Store to new table
    }
  }

  
  public String toString() {
    StringBuilder builder = new StringBuilder("[");
    
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) //??&& table[i].length > 0??
        builder.append(table[i]);
    }
    builder.append("]");
    return builder.toString();
  }

/** Define inner class for Entry */
public static class Entry<K, V> {
  K key;
  V value;

  public Entry(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  
  public String toString() {
    return "[" + key + ", " + value + "]";
  }
}
}