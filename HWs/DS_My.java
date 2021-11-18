// TODO: Add file header here


// TODO: Add class header here
public class DS_My implements DataStructureADT<String, String> {

  private String[][] list;
  private int size;
  private final int keyIndex = 1;
  private final int valueIndex = 0;

  // TODO may wish to define an inner class
  // for storing key and value as a pair
  // such a class and its members should be "private"

  // Private Fields of the class
  // TODO create field(s) here to store data pairs

  public DS_My() {
    list = new String[2][10]; //[value][key]
    size = 0;
  }

  @Override
  public void insert(String key, String value) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    } else if (this.contains(key)) {
      throw new RuntimeException("duplicate key");
    } else if (size < list[valueIndex].length) { // 数组未满
      list[valueIndex][size] = value;
      list[keyIndex][size] = key;
      size++;
    } else {
      String[][] temp = new String[2][list[valueIndex].length * 2];
      for (int i = 0; i < list[valueIndex].length; i++) {
        temp[valueIndex][i] = list[valueIndex][i];
        temp[keyIndex][i] = list[keyIndex][i];
      }

      list = temp;
      insert(key, value);
    }

  }

  @Override
  public boolean remove(String key) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    } else {
      for (int i = 0; i < size; i++) {
        if (list[keyIndex][i].equals(key)) {
          for (int j = i; j < size; j++) {
            list[keyIndex][j] = list[keyIndex][j + 1];
            list[valueIndex][j] = list[valueIndex][j + 1];
          }

          size--;
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public String get(String key) {
    if (key == null) {
      throw new IllegalArgumentException("null key");
    } else {
      for (int i = 0; i < size; i++) {
        if (list[keyIndex][i].equals(key)) {
          return list[valueIndex][i];
        }
      }
    }
    return null;
  }

  // throw IllegalArgumentException
  @Override
  public boolean contains(String key) {
    return get(key) != null;
  }

  @Override
  public int size() {
    return size;
  }


}
    
