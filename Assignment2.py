# Samet Emin Özen
# 2220765018

input_txt=open("doctors_aid_inputs.txt","r")  #we including input and output text
output_txt=open("doctors_aid_outputs.txt","a")
list_1=[]				#text line by line 
list_2=[]				# seperated version  list_1 word by word
persons=[] 				# persons will be here
text="" 				# input text file	
text=input_txt.read() 	
list_1=text.split("\n")	
commands=[]				# functions name and persons name will be here
	
def reading():
	global commands,list_2,list_1
	for line in list_1:
		
			  							#we are checking commands that come from user
		list_2=line.split(" ")			# and adding all of them to the commands
		for item in list_2:
			if item=="create" or item=="remove" or item=="probability" or item=="recommendation" :
				commands.append([item,list_2[list_2.index(item)+1]])
			elif item=="list":
				commands.append([item+"_func"])


def writing(line):
	output_txt.write("{}".format(line))    # writing to output file


def func_running():
	global commands
	for command in commands:
		
											# functions are running here
		if command[0]=="list_func":
			list_func()
		else:
			exec("writing({}(command))".format(command[0]))
		
		
	
def create(command):
	global persons
	person_list=[] 							# we are split the line as not have a command 
	person_list=list_1[commands.index(command)][7:].split(", ")
	persons.append(person_list)
	return ("Patient {} is recorded\n".format(person_list[0]))

	

def list_func():
	output_txt.write("""Patient	 Diagnosis  	Disease 	Disease 	Treatment	Treatment
Name 	 Accuracy    Name		Incidence 	Name 	 Risk
-------------------------------------------------------------------------------------\n""")
# list is writing directly table on the output file

	for li in persons:
		output_txt.write("{}{}%{}{}{}{}{}{}{}{}{}%\n".format(li[0],space_func(li[0]),100*float(li[1]),space_func(li[1]),li[2],space_func(li[2]),
			li[3],space_func(li[3]),li[4],space_func(li[4]),100*float(li[5])))
	

def remove(command):
	global persons
	for person in persons: 				# circulating in the persons list and if eqauls do these
		if person[0]== command[1]:
			persons.remove(person)
			return ("Patient {} is removed\n".format(person[0]))


def probability(command):				# if person there is not in the persons list return error 
	count=0
	for person in persons: 				
		if command[1] not in person:
			count+=1
			if count== len(persons):
				return "Probability for Su cannot be calculated due to absence.\n"
		if person[0]== command[1]:		

			rate_of_cancer=person[3].split("/")
			probability= round(int(rate_of_cancer[0])/((int(rate_of_cancer[1]))*round((1 - float(person[1])),5) + int(rate_of_cancer[0])),3)
			return ("{}'s probability is {}%\n".format(command[1],round(probability*100,2)))	
			

def recommendation(command):
	count=0
	for person in persons: 
		if command[1] not in person:  	#compare probability and treatment risk and return suggestment
			count+=1				# if person there is not in the persons list return error 
			count=0
			if count== len(persons):
				return "Recommendation for Su cannot be calculated due to absence.\n"		
		if person[0]==command[1]:
			rate_of_cancer=person[3].split("/")
			probability= round( int(rate_of_cancer[0])/((int(rate_of_cancer[1]))*round((1 - float(person[1])),5) + int(rate_of_cancer[0])),1)
			if float(person[5]) > probability:
				return ("System suggests {} NOT to have the treatment\n".format(person[0]))
			else:
				return ("System suggests {} to have the treatment\n".format(person[0]))


def space_func(word,space_interval=17):
	space=space_interval-len(word)			#set the space interval of the table
	return " "*space


reading() 
func_running()
