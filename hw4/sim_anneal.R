score =  function(x) { 
	return(max(4-abs(x), 2-abs(x-6), 2 - abs(x+6)))
}

prob = function(x,y,T) { 
	return(exp(-abs(x - y)/T))
}

T = function(x) { 
	return(2*(0.9)^x)
}


state_matrix <-  t(matrix(c(3,1,1,4,2,3,4,3,
						  0.102,0.223,0.504,0.493,0.312,0.508,0.982,0.887), 
							nrow = 2, ncol = 8, byrow = TRUE))


x = 2 # start state
state_space = seq(x-10,x+10)



for(i in 1:8) {

	p = prob(score(x),score(state_matrix[i,1]),T(i))
	# if p >= z, then accept the random successor state
	if(score(state_matrix[i,1]) > score(x)) { 
		x = state_matrix[i,1]
		state_space = seq(x-10,x+10)
	} else if(p >= state_matrix[i,2]){
		x = state_matrix[i,1]
		state_space = seq(x-10,x+10)
	} 

	cat("current state = ", x, " temperature = ", T(i), " Probability = ", p, '\n')


}