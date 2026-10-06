class Solution {
    public int lengthLongestPath(String input) {
        
        String lines[] = input.split("\n");
        int pathLen[] = new int[lines.length + 1];
        int maxLen = 0;

        for(String line : lines){
            int depth = line.lastIndexOf('\t') + 1;
            String name = line.substring(depth);

            if(name.contains(".")){
                int currentLen = pathLen[depth] + name.length();
                maxLen = Math.max(maxLen, currentLen);
            } else {
                pathLen[depth + 1] = pathLen[depth] + name.length() + 1;
            }
        }
        return maxLen;
    }
}