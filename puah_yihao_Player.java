class puah_yihao_Player extends Player {

    int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {

        // Cooperate on the first round
        if (n == 0) return 0;

        // Start with a tolerant strategy (Early Game)
        if (n <= 22) {
            // Calculate the total number of cooperations in the last round
            int totalCooperation = (oppHistory1[n - 1] == 0 ? 1 : 0) + (oppHistory2[n - 1] == 0 ? 1 : 0);
            // Defect if there was no cooperation
            return (totalCooperation >= 1) ? 0 : 1;
        } 
        // Utilize a weighted strategy to determine whether to defect (Middle Game)
        else if (n <= 85) {
            int playerCoop = 0;
            int opponentDefect = 0;

            // Calculate the total number of times player cooperated
            for (int i = 0; i < n; i++) {
                if (myHistory[i] == 0)
                    playerCoop += i;
            }

            // Calculate the total number of times opponents defected in a weighted manner
            for (int i = 0; i < n; i++) {
                if (oppHistory1[i] == 1){
                    opponentDefect += i;
                }
                if (oppHistory2[i] == 1){
                    opponentDefect += i;
                }
            }

            // Defect if opponents have defected more or equal times than player has cooperated
            return (opponentDefect >= playerCoop) ? 1 : 0;
        } 
        // Payoff Pavlov Strategy (Late Game)
        else {
            int lastPayoff = payoff[myHistory[n - 1]][oppHistory1[n - 1]][oppHistory2[n - 1]]; // Latest Move
            int prevPayoff =  payoff[myHistory[n - 2]][oppHistory1[n - 2]][oppHistory2[n - 2]]; // Move before Latest Move

            // Repeat last action if the payoff gain was successful
            return (lastPayoff >= prevPayoff) ? myHistory[n - 1] : (myHistory[n - 1] == 0) ? 1 : 0; // Switch from previous action
        }
    }
}
