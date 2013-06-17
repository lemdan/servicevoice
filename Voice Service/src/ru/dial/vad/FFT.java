package ru.dial.vad;

public class FFT {
	/// <summary>
    /// Вычисление поворачивающего модуля e^(-i*2*PI*k/N)
    /// </summary>
    /// <param name="k"></param>
    /// <param name="N"></param>
    /// <returns></returns>
    private static Complex w(int k, int N)
    {
        if (k % N == 0) return new Complex(1);
        double arg = -2 * Math.PI * k / N;
        return new Complex(Math.cos(arg), Math.sin(arg));
    }
    /// <summary>
    /// Возвращает спектр сигнала
    /// </summary>
    /// <param name="x">Массив значений сигнала. Количество значений должно быть степенью 2</param>
    /// <returns>Массив со значениями спектра сигнала</returns>
    public static Complex[] fft(byte[] x){
    	Complex[] arrayComplex = new Complex[x.length];
    	for (int i = 0; i < x.length; i++){
    		arrayComplex[i] = new Complex(x[i]);
    	}
    	
    	return fft(arrayComplex);
    }
    
    
    public static Complex[] fft(Complex[] x)
    {
        Complex[] mag;
        int N = x.length;
        if (N == 2)
        {
            mag = new Complex[2];
            mag[0] = x[0].plusComplex(x[1]);
            mag[1] = x[0].minusComplex(x[1]);
        }
        
        
        
        else{
            Complex[] x_even = new Complex[N / 2];
            Complex[] x_odd = new Complex[N / 2];
            for (int i = 0; i < N / 2; i++)
            {
                x_even[i] = x[2 * i];
                x_odd[i] = x[2 * i + 1];
            }
            Complex[] X_even = fft(x_even);
            Complex[] X_odd = fft(x_odd);
            mag = new Complex[N];
            for (int i = 0; i < N / 2; i++)
            {
                mag[i] = Complex.plusComplex(X_even[i] , Complex.mulComplex(w(i, N) , X_odd[i]));
                mag[i + N / 2] = Complex.minusComplex(X_even[i], Complex.mulComplex(w(i, N) , X_odd[i]));
            }
        }
        return mag;
    }
}
