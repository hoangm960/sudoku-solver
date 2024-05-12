package GameScreen.helper;

public class DifficultyMap {
    private String[] _key_set;
    private Difficulty[] _value_arr;
    private int _size;

    public DifficultyMap(int max_size) {
        this._key_set = new String[max_size];

        this._value_arr = new Difficulty[max_size];
        this._size = 0;
    }

    public void put(String key, Difficulty value) {
        if (!containsKey(key)) {
            _key_set[_size] = key;
            _value_arr[_size] = value;
            _size++;
        } else {
            update(key, value);
        }
    }

    public Difficulty get(String key) {
        for (int i = 0; i < _size; i++) {
            if (_key_set[i].equals(key)) {
                return _value_arr[i];
            }
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void update(String key, Difficulty value) {
        for (int i = 0; i < _size; i++) {
            if (_key_set[i].equals(key)) {
                _value_arr[i] = value;
            }
        }
    }
}