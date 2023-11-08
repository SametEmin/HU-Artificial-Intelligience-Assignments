
#Samet Emin Özen
#2220765018



alphabet=['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 
'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z']

#we are defining variables

output_txt=open("output.txt","w")
input_txt=open("input.txt","r")
text="" 				
text=input_txt.read() 
list_1=text.split("\n")
list_1=[i.split(" ") for i in list_1 ]
counter=0
categ_list=[]
fixed_categ_list=["category-%d%s"%(j,i) for j in range(1,3) for i in alphabet[:6]]
stu=0
ful=0
ssn=0




def CREATECATEGORY(category,size):
	global counter
	counter+=1
	exmpl=dict()
	alpha=alphabet[0:size]
	alpha=alpha[::-1]
					#alphabe defining
	
	for lin in range(size):
		for col in range(size):				#we are defining every single place is X
			exmpl[(alpha[lin],col)]="X"
	exmpl["name"]=category
	globals()["dict{}".format(counter)]=exmpl

	output_txt.write("The category '%s' having %d seats has been created\n"%(exmpl["name"],size**2))

	

def SELLTICKET(li):
    
	cnt=0
	cnt2=1
	list_2=[]
	var=""
	deleted_line=""
	for plc in li[3:]:
		if (len(plc)==2 or len(plc)==3 ) :
			cnt+=1
			globals()["plc%d"%cnt]=(plc[0],int(plc[1:]))			#I created variables with globals()
			
			
			
			cnt2+=1
			list_2.append(plc)
		else:
			cnt+=1
			globals()["plc%d"%cnt]=(plc[0],int(plc.split("-")[0][1:]))
			
			
			cnt+=1
			globals()["plc%d"%cnt]=(plc[0],int(plc.split("-")[1]))


			cnt2+=1
			
			list_2.append(plc)
									#i created place variables with globals

		
			

	for l in range(1,counter+1):
		
		if list(eval("dict{}".format(l)).values())[-1]==li[2] :
			ctgry=eval("dict{}".format(l))
			
			for j in range(1,cnt+1):



				try:
					
					

					if ctgry[eval("plc%d"%j)]=="X":   	
						
						

						ctgry[eval("plc%d"%j)]= li[1][0].upper()

						

					else:
						for k in list_2:

							deleted_line="Warning: The seats {} cannot be sold to {} due some of them have already been sold\n".format(eval("plc%d"%j)[0]+"-"+str(eval("plc%d"%j)[1]))+" "+li[0]
							output_txt.write(deleted_line)
							

					

				except (KeyError,IndexError):
					output_txt.write("Error: The category '{}' has less column than the specified index {}!\n".format(li[2],eval("plc%d"%j)[0]+"-"+str(eval("plc%d"%j)[1])))
					var=eval("plc%d"%j)[0]+"-"+str(eval("plc%d"%j)[1])
			for m in range(cnt2-1):
			
			
			
				if 	"Success: %s has bought %s at %s\n"%(li[0],list_2[m],li[2])	!= deleted_line:
					output_txt.write("Success: %s has bought %s at %s\n"%(li[0],list_2[m],li[2]))

			
				
def CANCELTICKET(lst):
	for i in range(1,counter+1):
		
		if list(eval("dict{}".format(i)).values())[-1]==lst[0]:
																	#tickets are cancelling here
			dct=eval("dict{}".format(i))

			dct[("%s"%lst[1][0],int(lst[1][1:]))]="X"


	
def BALANCE(category):
	global stu ,ssn,ful

	for i in range(1,counter+1):
		
		if list(eval("dict{}".format(i)).values())[-1]==category:

			table=eval("dict{}".format(i))
			for i in list(table.values()):					#I calculated moneys and tickets here 
				if i=="S":
					stu+=1
				elif i=="T":
					ssn+=1
				elif i=="F":
					ful+=1

	
	output_txt.write("category report of '%s'\n"%category)
	output_txt.write("--------------------------------\n")
	output_txt.write("Sum of students = %d, Sum of full pay = %d, Sum of season ticket = %d, and Revenues = %d Dollars\n "%(stu,ful,ssn,stu*10+ful*20+ssn*250))



def SHOWCATEGORY(category):
	table=[]
	output_txt.write("Printing category layout of %s\n"%category)
	for i in range(1,counter+1):
		
		if list(eval("dict{}".format(i)).values())[-1]==category:




			table=eval("dict{}".format(i))

			size=list(table.keys())[-2][1]+1

			coordinates=[i for i in table.keys()][0:-1]
			values=[i for i in table.values()][0:-1]
			count=0
			output_txt.write("\n")
			
			for lin in range(size):
				output_txt.write("{} ".format(coordinates[(lin*size)][0]))
				for col in range(size):
					
					place=values[lin*size+col]
					output_txt.write("{}  ".format(place))
					count+=1
				
				
				output_txt.write("\n")
			output_txt.write(" ")
			for i in range(size):
				output_txt.write("{} {}".format((2-len(str(i)))*" ",i))
	output_txt.write("\n")


def func_run():			#functions are running here by helping if 
	for i in list_1:
		if i[0]=="CREATECATEGORY" and i[1] not in categ_list and i[1] in fixed_categ_list:
			category=i[1]
			size=i[2]
			size=size.split("x")							
			size=int(size[0])
			CREATECATEGORY(category,size)
			categ_list.append(i[1])
		elif i[1] in categ_list and i[0]=="CREATECATEGORY" :
			output_txt.write("Warning: Cannot create the category for the second time. The stadium has already %s\n"%i[1])
			
			
		elif i[0]=="SHOWCATEGORY":
			
			SHOWCATEGORY(i[1])
		elif i[0]=="SELLTICKET":
			SELLTICKET(i[1:])

		elif i[0]=="CANCELTICKET":

			CANCELTICKET(i[1:])
			

		elif i[0]=="BALANCE":
			BALANCE(i[1])



func_run()






