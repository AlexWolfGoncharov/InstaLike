package com.github.alexwolfgoncharov.instalike.main;

import java.util.Date;

//import org.apache.log4j.Logger;

/**
 * Created by alexwolf on 25.12.15.
 */
public class StartLike {


    private static boolean first = true;
    private static Like like;




        public static void main(String[] args) {

            Thread thread = null;

            Date start = new Date();

            while(true){


                if (first){
                    like =  new Like();
                    first = false;
                }
//                System.out.println(like.getStatus());

                switch (like.getStatus()){
                    case "start":
                        System.out.println("start");
                        thread = new Thread(like);
                        start = new Date();
                        System.out.println(start.toString());
                        thread.start();
                        break;
                    case "run":
                        break;
                    case "error":
                        like.stopDriver();
                        thread.interrupt();
                        first = false;
                        break;
                    case "stop":
                        like.needStart();
                        first = false;
                        break;




                }


                long rez = new Date().getTime() - start.getTime();
//                Делаем перезапуск каждые 15 минут
                long etalon = 1000*60*15;
//                System.out.println(rez);
                if (rez > etalon){
                    System.out.println("Прошла 1 минута делаем перезапуск");

                    like.stopDriver();
                    thread.interrupt();
                    first = false;


                }

//                System.out.println(thread.getStackTrace().toString());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }





}
