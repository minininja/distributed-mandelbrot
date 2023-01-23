package org.dorkmaster.mandlebrot.renderer;

import com.hazelcast.cluster.Member;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TargetSelector {
    protected List<Member> members;
    protected Random r = new SecureRandom();

    public TargetSelector(Set<Member> members, Member member) {
        Set<Member> tmp = members.stream().collect(Collectors.toSet());
        if (members.size() > 1 && null != member) {
            tmp.remove(member);
        }
        this.members = tmp.stream().collect(Collectors.toList());
    }

    public TargetSelector(Set<Member> members) {
        this(members, null);
    }

    public Member nextMember() {
        // pick at random
        return members.get(r.nextInt(members.size()));
    }
}
