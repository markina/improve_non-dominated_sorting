#!/bin/sh

if [ "$1" == "clean" ]
then
    echo "i can't clean yet"
else
    pdflatex thesis.tex
    biber thesis
    pdflatex thesis.tex
    pdflatex thesis.tex

fi