import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Worker {
    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance hi = Hazelcast.newHazelcastInstance();

        hi.getCluster().addMembershipListener(new ClusterMembershipListener());
        Thread.sleep(60 * 60 * 1000);
    }

    public static class ClusterMembershipListener implements MembershipListener {

        public void memberAdded(MembershipEvent membershipEvent) {
            System.err.println("Added: " + membershipEvent);
        }

        public void memberRemoved(MembershipEvent membershipEvent) {
            System.err.println("Removed: " + membershipEvent);
        }
    }
}
