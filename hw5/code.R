

dir <- '/home/adithya/Desktop/SPRING2017/CS540/WARC201709/'

filenames <- list.files(dir)

words <- list()

for(i in 1:length(filenames)) { 

	l <- readLines(paste0(dir,filenames[i]))
	words[[i]] <- unlist(strsplit(l, " ",fixed=T))

}	
words <- unlist(words)
t<- table(words)


# question 5
plot(ranks, sort(t, decreasing=T), cex = 0.5, col = 'red', yaxt = 'n', 
	xlab = "ranks",ylab = "counts", ylim = c(0,8000))
axis(2, at=seq(0,8000,1000), labels = TRUE)

# question 6
plot(log10(ranks), log10(sort(t, decreasing=T)), cex = 0.5, col = 'red', yaxt = 'n', 
	xlab = "log-scaled ranks",ylab = "log-scaled counts")

axis(2, at=seq(0,max(log10(sort(t, decreasing=T))),0.10), labels = TRUE)

