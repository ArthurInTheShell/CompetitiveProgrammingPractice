
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class HHaiku {
  public static void main(String[] args) {
    for (String l : solve())
      System.out.println(l);
  }

  public static String[] solve() {
    Scanner scan = new Scanner(System.in);
    String line = scan.nextLine();
    String[] org = new String[] { line };
    String[] ret = new String[3];
    scan.close();
    String[] words = line.split(" ");
    int[] syls = new int[words.length];
    int totalSyls = 0;
    for (int i = 0; i < words.length; i++) {
      syls[i] = count(handleSpecial(words[i]));
      // System.out.println(syls[i]);
      totalSyls += syls[i];
      if (totalSyls > 17)
        return org;
    }
    if (totalSyls != 17)
      return org;
    int w = 0;
    int start = 0;
    int syl = 0;
    StringBuilder build = new StringBuilder();
    for (; w < words.length; w++) {
      syl += syls[w];
      if (syl == 5)
        break;
      if (syl > 5)
        return org;
    }
    if (syl != 5)
      return org;
    for (int i = start; i < w; i++) {
      build.append(words[i]).append(" ");
    }
    build.append(words[w]);
    ret[0] = build.toString();
    build.setLength(0);
    syl = 0;
    w++;
    start = w;
    for (; w < words.length; w++) {
      syl += syls[w];
      if (syl == 7)
        break;
      if (syl > 7)
        return org;
    }
    if (syl != 7)
      return org;
    for (int i = start; i < w; i++) {
      build.append(words[i]).append(" ");
    }
    build.append(words[w]);
    ret[1] = build.toString();
    build.setLength(0);
    syl = 0;
    w++;
    start = w;
    for (; w < words.length; w++) {
      syl += syls[w];
      if (syl == 5)
        break;
      if (syl > 5)
        return org;
    }
    if (syl != 5)
      return org;
    for (int i = start; i < w; i++) {
      build.append(words[i]).append(" ");
    }
    build.append(words[w]);
    ret[2] = build.toString();
    build.setLength(0);
    return ret;
  }

  public static int count(String s) {
    int syl = 0;
    boolean onVowel = false;
    AtomicInteger i = new AtomicInteger(0);
    for (; i.get() < s.length() + 1;) {
      if (i.get() >= s.length() || isNonChar(s, i)) {
        int ii = i.get();
        if (ii - 2 >= 0 && (s.charAt(ii - 1) == 's') && (s.charAt(ii - 2) == 'e')) {
          // S rule
          boolean isLastVowel = ii - 3 >= 0 && isVowel(s, new AtomicInteger(ii - 3));
          boolean isSecondVowel = ii - 4 >= 0 && isVowel(s, new AtomicInteger(ii - 4));
          if (!isLastVowel && isSecondVowel) {
            syl--;
          }
        }
        break;
      }
      if (isVowel(s, i)) {
        if (!onVowel) {
          onVowel = true;
          syl++;
        }
      } else {
        if (onVowel) {
          onVowel = false;
        }
      }
    }
    return Math.max(1, syl);
  }

  public static boolean isVowel(String s, AtomicInteger i) {
    int ii = i.get();
    char c = s.charAt(ii);
    switch (c) {
    case 'y':
      i.set(ii + 1);
      if (ii < s.length() - 1 && isAEIOU(s.charAt(ii + 1)))// isVowel(s, new AtomicInteger(ii + 1)))
        return false;
      return true;
    case 'u':
    case 'a':
    case 'e':
    case 'i':
    case 'o':
      i.set(ii + 1);
      return true;
    default:
      i.set(ii + 1);
      return false;
    }// return is vowel and ulter i to be the next index to look at
  }

  public static String handleSpecial(String s) {
    return handleQU(handleLastE(s.toLowerCase()));
  }

  public static String handleQU(String s) {
    return s.replaceAll("qu", "z");
  }

  public static String handleLastE(String s) {
    AtomicInteger i = new AtomicInteger(s.length() - 1);
    for (; i.get() >= 0;) {
      if (isNonChar(s, i)) {
        i.set(i.get() - 1);
      } else {
        char c = s.charAt(i.get());
        if (c == 'e') {
          int ii = i.get();
          if (ii - 1 >= 0 && (s.charAt(ii - 1) == 'l')) {
            if (ii - 2 >= 0 && !isVowel(s, new AtomicInteger(ii - 2))) {
              break;
            }
          }
          return s.substring(0, ii) + "-" + s.substring(ii + 1);
        }
        break;
      }
    }
    return s;
  }

  public static boolean isNonChar(String s, AtomicInteger i) {
    char c = s.charAt(i.get());
    if (c >= 'a' && c <= 'z')
      return false;
    return true;
  }

  public static boolean isAEIOU(char c) {
    return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
  }
}
