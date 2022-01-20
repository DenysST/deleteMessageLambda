package com.aws.learn;


import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.chime.ChimeClient;

public class AmazonChimeService {
    private static final String ACCESS = "";
    private static final String SECRET = "";

    static AwsBasicCredentials awsCreds = AwsBasicCredentials.create(ACCESS, SECRET);
    public static ChimeClient getClient() {
        return ChimeClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.US_EAST_1)
                .build();
    }

}
