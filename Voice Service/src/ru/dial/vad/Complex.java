package ru.dial.vad;

public class Complex {


		   private double re, im;                   // Действительная и мнимая часть

		   public Complex(double re, double im) { 
		      this.re = re; this.im = im;
		   }
		   
		   public Complex(double re){this(re, 0.0); }

		   public Complex(){this(0.0, 0.0); }

		   public Complex(Complex z){this(z.re, z.im) ; }
		   
		   public Complex mulComplex(Complex b){
			   return mulComplex(this, b);
		   }
		   
		   public Complex plusComplex(Complex b){
			   return plusComplex(this, b);
		   }
		   
		   public Complex minusComplex(Complex b){
			   return minusComplex(this, b);
		   }
		   
		   public Complex divComplex(Complex b){
			   return divComplex(this, b);
		   }
		   
		   public static Complex mulComplex(Complex a, Complex b){
			   return new Complex(a.getRe() * b.getRe() - a.getIm() * b.getIm(), a.getIm() * b.getIm() +  a.getIm() * b.getRe());
			  
		   }
		   
		   public static Complex plusComplex(Complex a, Complex b){
			   return new Complex(a.getRe() + b.getRe(), a.getIm() + b.getIm());
		   }
		   
		   public static Complex minusComplex(Complex a, Complex b){
			   return new Complex(a.getRe() - b.getRe(), a.getIm() - b.getIm());
		   }
		   
		   public Complex divComplex(Complex a, Complex b){ 

			   double mod = Math.sqrt(Math.pow(b.getRe(), 2) + Math.pow(b.getIm(), 2)); 

			   return new Complex(

			      (a.getRe() * b.getRe() - a.getIm() * b.getIm()) / mod, (a.getIm() * b.getIm() - a.getRe() * b.getIm()) / mod);

			}

		public double getRe() {
			return re;
		}

		public double getIm() {
			return im;
		}

		public void setRe(double re) {
			this.re = re;
		}

		public void setIm(double im) {
			this.im = im;
		}
}
