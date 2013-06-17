package ru.dial.mgapi;



import javax.sound.sampled.AudioFormat;


/**
 * extracting PCM data from byteArray
 * 
 * @author Lemdan
 */
public class WaveData {


        private float[] audioData;

        public WaveData() {
        }


        public float[] getAudioData() {
                return audioData;
        }




        /**
         * for extracting amplitude array the format we are using :16bit, 22khz, 1
         * channel, littleEndian,
         * 
         * @return PCM audioData
         * @throws Exception
         */

        public float[] extractFloatDataFromAmplitudeByteArray(AudioFormat format, byte[] audioBytes) {
                // convert
                audioData = null;
                if (format.getSampleSizeInBits() == 16) {
                        int nlengthInSamples = audioBytes.length / 2;
                        audioData = new float[nlengthInSamples];
                        if (format.isBigEndian()) {
                                for (int i = 0; i < nlengthInSamples; i++) {
                                        /* First byte is MSB (high order) */
                                        int MSB = audioBytes[2 * i];
                                        /* Second byte is LSB (low order) */
                                        int LSB = audioBytes[2 * i + 1];
                                        audioData[i] = MSB << 8 | (255 & LSB);
                                }
                        }
                        else {
                                for (int i = 0; i < nlengthInSamples; i++) {
                                        /* First byte is LSB (low order) */
                                        int LSB = audioBytes[2 * i];
                                        /* Second byte is MSB (high order) */
                                        int MSB = audioBytes[2 * i + 1];
                                        audioData[i] = MSB << 8 | (255 & LSB);
                                }
                        }
                }
                else if (format.getSampleSizeInBits() == 8) {
                        int nlengthInSamples = audioBytes.length;
                        audioData = new float[nlengthInSamples];
                        if (format.getEncoding().toString().startsWith("PCM_SIGN")) {
                                for (int i = 0; i < audioBytes.length; i++) {
                                        audioData[i] = audioBytes[i];
                                }
                        }
                        else {
                                for (int i = 0; i < audioBytes.length; i++) {
                                        audioData[i] = audioBytes[i] - 128;
                                }
                        }
                }// end of if..else
                        // System.out.println("PCM Returned===============" +
                        // audioData.length);
                return audioData;
        }

}
