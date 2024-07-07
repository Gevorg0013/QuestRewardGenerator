public class QuestRewardGenerator {
    // For sigmoid function transitions from 0 to 1 - speed
    private static final double SIGMOID_TRSANITION_FRACTION = 0.1;
    // To allow thorugh sin wave transition 'b' <-> 's' mulitple times
    private static final double SIN_TRANSITION_COMPONENT = 10.0;
    
    /**
     * Generate random rewards
     * @param n The rewards number to be generated
     * 
     * @return The generated string of reword characters
     */
    public static String generateRewards(int n) {
        // The final rewards generator/builder
        final StringBuilder rewards = new StringBuilder(n);

        final double midpoint = n / 2.0;
        // For each quest position i from 0 to n-1 determine the value
        for (int i = 0; i < n; ++i) {
            // Calculate the probability of getting a silver chest at position i
            final double probabilityOfSilver = predictState(i, (double) i / n, midpoint);
            
            // Append 's' if the probability is greater than 50%, otherwise append 'b'
            if (probabilityOfSilver > 0.5) {
                rewards.append('s');
            } else {
                rewards.append('b');
            }
        }
        
        return rewards.toString();
    }

    /**
     * Determine the reward state probability using sigmoid and sine functions
     * @param position The position in string to determine the reward
     * @param relativePosition The relative position in between (0, 1] based on pos/N
     * @param midpoint The midpoint of the rewards list - N / 2
     * 
     * @return The probability state, where <= 0.5 indicates 's' and > 0.5 indicates 'b'
     */
    private static double predictState(int position, double relativePosition, double midpoint) {
        // Sigmoid function component to create an overall trend from 'b' to 's'
        final double sigmoidComponent = 1. / (1. + Math.exp(-SIGMOID_TRSANITION_FRACTION * (position - midpoint)));

        // Sine function component to introduce multiple transitions between 'b' and 's'
        final double sineComponent = 0.5 * Math.sin(SIN_TRANSITION_COMPONENT * Math.PI * relativePosition) + 0.5;

        // Combine the sigmoid and sine components by averaging them
        return (sigmoidComponent + sineComponent) / 2.;
    }

    public static void main(String[] args) {
        // Example usages: Generate a sequence of rewards for [50, 70) quests
        for (int i = 50; i < 70; ++i) {
            final String rewards = generateRewards(i);
            System.out.println(rewards);
        }
    }
}

