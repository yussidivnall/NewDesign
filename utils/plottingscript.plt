#!/usr/bin/gnuplot
set terminal png
set output "out.png"
plot '/tmp/graphplot.txt' using 1:2 with lines title "2", '/tmp/graphplot.txt' using 1:3 with lines title "3", '/tmp/graphplot.txt' using 1:4 with lines title "4", '/tmp/graphplot.txt' using 1:5 with lines title "5", '/tmp/graphplot.txt' using 1:6 with lines title "6", '/tmp/graphplot.txt' using 1:7 with lines title "7"
