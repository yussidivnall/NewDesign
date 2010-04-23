#!/usr/bin/perl
$infile=@ARGV[0];
open FILE, "<","$infile" or die;
while(<FILE>){
	print $_;
}

