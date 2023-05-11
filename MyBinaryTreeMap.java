public class MyBinaryTreeMap<K> {
    private int size = 0;
    private Node root = null;

    private class Node {
        public K key;
        public Node left = null;
        public Node right = null;
        public Node (K key) {
            this.key = key;
        }
    }

    public K put(K key) {
        if (root == null) {
            root = new Node(key);
            size++;
            return null;
        }
        return putHelper(key, root);
    }

    private K putHelper(K key, Node node) {
        Comparable<K> k = (Comparable<K>) key;
        if (k.compareTo(node.key) < 0) {
            if (node.left == null) {
                node.left = new Node(key);
                size++;
                return null;
            }
            return putHelper(key, node.left);
        }
        if (k.compareTo(node.key) > 0) {
            if (node.right == null) {
                node.right = new Node(key);
                size++;
                return null;
            }
            return putHelper(key, node.right);
        }
        if (k.compareTo(node.key) == 0) {
            if (node.left == null) {
                node.left = new Node(key);
                size++;
                return null;
            }
            if (node.left != null) {
                Node child = new Node(key);
                child.left = node.left;
                node.left = child;
                size++;
                return null;
            }
        }
        return null;
    }

    public K get(Object key) {
        Node node = findNode(key);
        if (node == null) return null;
        return node.key;
    }

    private Node findNode(Object target) {
        Comparable<K> t = (Comparable<K>) target;
        Node node = root;
        while (node != null) {
            if (t.compareTo(node.key) < 0) node = node.left;
            if (t.compareTo(node.key) > 0) node = node.right;
            if (t.compareTo(node.key) == 0) return node;
        }
        return null;
    }

    private Node findParent(Object target) {
        Comparable<K> t = (Comparable<K>) target;
        Node child = root;
        Node parent = root;
        while (child != null) {
            if (t.compareTo(child.key) < 0) {
                parent = child;
                child = child.left;
            }
            if (t.compareTo(child.key) > 0) {
                parent = child;
                child = child.right;
            }
            if (t.compareTo(child.key) == 0) return parent;
        }
        return null;
    }

    public K remove(Object key) {
        K oldKey = get(key);
        if (oldKey == null) return null;
        if (key == root.key) root = removeHelper(key);
        else removeHelper(key);
        System.out.println("Root = " + root.key);
        return oldKey;
    }

    private Node removeHelper(Object key) {
        Node child = findNode(key);
        Node parent = findParent(key);
        if (child.left == null && child.right == null) {
            if (child == parent.left) parent.left = null;
            if (child == parent.right) parent.right = null;
            size--;
            return parent;
        }
        if (child.right == null) {
            if (child == parent.left) parent.left = child.left;
            if (child == parent.right) parent.right = child.left;
            size--;
            return parent;
        }
        if (child.left == null) {
            if (child == parent.left) parent.left = child.right;
            if (child == parent.right) parent.right = child.right;
            size--;
            return parent;
        }
        Node tempNode = findSmallest(child.right);
        removeHelper(tempNode.key);
        child.key = tempNode.key;
        return parent;
    }

    private Node findSmallest(Node node) {
        if (node.left == null) return node;
        return findSmallest(node.left);
    }

    public void printTree() {
        LER(root);
        System.out.println("-------------");
    }

    private void LER(Node node) {
        if (node.left != null) LER(node.left);
        System.out.println(node.key);
        if (node.right != null) LER(node.right);
    }
}
