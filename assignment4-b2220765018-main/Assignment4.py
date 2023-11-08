#Samet Emin Özen
#2220765018

import string
import sys
alphabet=list(string.ascii_uppercase)[:10]
executions=""

#I am catching error and exceptions at below
try:
    txt1=open(sys.argv[1],"r")
except:
    executions+="Player1.txt "
try:
    txt2=open(sys.argv[2],"r")
except:
    executions+="Player2.txt "
try:
    inp1=open(sys.argv[3],"r")
except:
    executions+="Player1.in "
try:
    inp2=open(sys.argv[4],"r")
except:
    executions+="Player2.in "
try:
    opt1=open("OptionalPlayer1.txt","r")
except:
    executions+="OptionalPlayer1.txt "
try:
    opt2=open("OptionalPlayer2.txt","r")
except:
    executions+="OptionalPlayer2.txt "
try:
    output=open("Battleship.out","w")
except:
    executions+="Battleship.out "
if len(executions)!=0:
    executions=executions[:-1]    #i am writing if they exist
    print("IOError: input file(s) %s is/are not reachable."%", ".join(map(str,executions.split(" "))))
else:
    try:
        carrier,battleship,destroyer,submarine,patrol_boat=[],[],[],[],[]
        carrier2,battleship2,destroyer2,submarine2,patrol_boat2=[],[],[],[],[]

        #this is for comparison with players

        coordinates=[ (i,j) for i in range(1,11) for j in alphabet[:10]] # that is coordinates like (A,1) (A,2)...

        def posit_def(dict_pos,list1,list2,file):
            globals()[dict_pos]=dict()
            globals()[list1]=file.read()
            globals()[list1]=eval(list1).split("\n")
            globals()[list1]=[i.split(";") for i in eval(list1)]
            globals()[list2]=[j for i in eval(list1) for j in i]
            for (i,j) in zip(coordinates,eval(list2)):
                if j=='':
                    j="-"
                eval(dict_pos)[i]=j

        posit_def("dict_1", "list_1", "list_2", txt1)
        posit_def("dict_a", "list_a", "list_b", txt2)    

        dict_hitted1={co:"-" for co in coordinates  }
        dict_hitted2=dict_hitted1.copy()

        

        # I did this because not to repeat
        #i did same things like on upward
        def input_def(list_name,file):
            globals()[list_name]=file.read()
            globals()[list_name]=eval(list_name).replace("\n","")
            globals()[list_name]=eval(list_name).split(";")

        input_def("list_1i",inp1)
        input_def("list_ai",inp2)


       
        # i am recreate input lists

        list_1i=[(int(i.split(",")[0]),i.split(",")[1] ) for i in list_1i[:-1] ]
        list_ai=[(int(i.split(",")[0]),i.split(",")[1] ) for i in list_ai[:-1] ]



        def opt_def(list_opt_ex,file):
            # using additional files

            globals()[list_opt_ex]=file.read()
            globals()[list_opt_ex]=eval(list_opt_ex).split(";\n")
            globals()[list_opt_ex]=[i.split(":") for i in eval(list_opt_ex)]
            for i in eval(list_opt_ex):
                for j in i:
                    if i.index(j)==1:
                        eval(list_opt_ex)[  eval(list_opt_ex).index(i)][i.index(j)]=j.split(";")

        opt_def("list_opt", opt1)
        opt_def("list_opt2", opt2)
        
        def bothWriting(text): # Writing function
            output.write(text)
            print(text,end="")


        def processing(list_name,car,dest,sub,list_opt_name,battle,patrol):
            # i am gonna loop in the player1.txt and i will find ships.
            for ships in range(len(list_name)):
                for ship in range(len(list_name[ships])):
                    if list_name[ships][ship]== "C"  or list_name[ships][ship]== "D" or list_name[ships][ship]== "S" :
                       
                        if list_name[ships][ship]== "C":
                            
                            car.append(coordinates[ships*10+ship])  
                        
                        elif list_name[ships][ship]== "D":
                            dest.append(coordinates[ships*10+ship])  

                        elif list_name[ships][ship]== "S":
                            sub.append(coordinates[ships*10+ship]) 
                
            for i in range(2):
                a=list()
                # reading additional files
                if list_opt_name[0][1][1]=="right":
                    for i in range(4):
                        a.append((int(list_opt_name[0][1][0].split(",")[0]) , chr(ord(list_opt_name[0][1][0].split(",")[1] )+i ) ))
                        
                    list_opt_name.pop(0)
                    battle.append(a )   #i appending ships coordinates to a list


                elif  list_opt_name[0][1][1]=="left":
                    for i in range(4):
                        a.append((int(list_opt_name[0][1][0].split(",")[0]) , chr(ord(list_opt_name[0][1][0].split(",")[1] )+i ) ))
                        
                    list_opt_name.pop(0)
                    battle.append(a )   #i appending ships coordinates to a list


                elif list_opt_name[0][1][1]=="up":
                    for i in range(4):
                        a.append((int(list_opt_name[0][1][0].split(",")[0])-i , chr(ord(list_opt_name[0][1][0].split(",")[1] ) ) ))                   
                    
                    battle.append( a)   #i appending ships coordinates to a list
                        
                    list_opt_name.pop(0)
                

                elif list_opt_name[0][1][1]=="down":
                    
                    for i in range(4):
                        a.append((int(list_opt_name[0][1][0].split(",")[0])+i , chr(ord(list_opt_name[0][1][0].split(",")[1] ) ) ))                   
                    
                    battle.append( a)   #i appending ships coordinates to a list
                        
                    list_opt_name.pop(0)

            for i in range(4):  #ship numbesrs
                a=list()
                #again reading
                if list_opt_name[0][1][1]=="right":
                    for i in range(2):
                        a.append((int(list_opt_name[0][1][0].split(",")[0]) , chr(ord(list_opt_name[0][1][0].split(",")[1] )+i ) ))
                        
                    list_opt_name.pop(0)
                    patrol.append(a )   #i appending ships coordinates to a list


                elif  list_opt_name[0][1][1]=="left":
                    for i in range(2):
                        a.append((int(list_opt_name[0][1][0].split(",")[0]) , chr(ord(list_opt_name[0][1][0].split(",")[1] )+i ) ))
                        
                    list_opt_name.pop(0)
                    patrol.append(a )   #i appending ships coordinates to a list


                elif list_opt_name[0][1][1]=="up":
                    for i in range(2):
                        a.append((int(list_opt_name[0][1][0].split(",")[0])-i , chr(ord(list_opt_name[0][1][0].split(",")[1] ) ) ))                   
                    
                    patrol.append( a)   #i appending ships coordinates to a list
                        
                    list_opt_name.pop(0)
                

                elif list_opt_name[0][1][1]=="down": 
                    for i in range(2):
                        a.append((int(list_opt_name[0][1][0].split(",")[0])+i , chr(ord(list_opt_name[0][1][0].split(",")[1] ) ) ))                   
                    patrol.append( a)   #i appending ships coordinates to a list        
                    list_opt_name.pop(0)

        processing(list_1, carrier, destroyer, submarine,list_opt,battleship,patrol_boat)
        processing(list_a, carrier2, destroyer2, submarine2,list_opt2,battleship2,patrol_boat2)

        def adding_in_dict(dict_name,car,battle,dest,sub,patrol):  # Creating dict for ships
            for i in [car,battle,dest,sub,patrol]:
                if i ==battle or i==patrol:
                    if i ==battle:
                        for j in i:
                            for k in j:
                                dict_name[k]="B"
                    elif i==patrol:
                        for j in i:
                            for k in j:
                                dict_name[k]="P"
                else:
                    if i==car:
                        for j in i:
                            dict_name[j]="C"
                    elif i==dest:
                        for j in i:
                            dict_name[j]="D"
                    elif i==sub:
                        for j in i:
                            dict_name[j]="S"

        adding_in_dict(dict_1, carrier, battleship, destroyer, submarine, patrol_boat) #for player 1
        adding_in_dict(dict_a, carrier2, battleship2, destroyer2, submarine2, patrol_boat2)  # for player2
        table1=""  #for player 1
        table2="" #for player2
        def table_Creator(table,_dict_):
            global table1,table2  
            for line in range(10):
                table+=str(line+1)+ (2-len(str(line+1)))*" "
                for col in range(10):
                    table+=list(_dict_.values())[line*10+col]+" "
                table+="\n"
            if "%s"%table=="table1":
                table1=table
            elif "%s"%table=="table1":
                table2=table
            return table

        def sub_comparison(_list_,_dict_,hitted,car,dest,sub,bat,pat,cnt): #input list, hitten dict
            if _dict_[_list_[cnt]]!="-":       # I compare player1 input with player2 places
                    hitted[_list_[cnt]]="X"
                    for gemiler in [car,dest,sub] :
                        for gemi in gemiler:
                            if gemi ==_list_[cnt]:
                                gemiler.remove( _list_[cnt] ) 
                        for gemiler in [bat,pat]:
                            for gemi in gemiler:
                                for gem in gemi:
                                    if gem==_list_[cnt]:
                                        gemiler[gemiler.index(gemi)].remove( _list_[cnt] )
            else:
                hitted[_list_[cnt]]="O"

        def shipFinder(car,dest,sub,pat,bat,car2_,dest2_,sub2_,pat2_,bat2_):
            
            for gemiler in [car,dest,sub] :
                    if len(gemiler)==0 and gemiler==car:
                        car2_="X"
                    if len(gemiler)==0 and gemiler==dest:
                        dest2_="X"
                    if len(gemiler)==0 and gemiler==sub:
                        sub2_="X"

            numb=0
            numa=0
            for gemiler in [pat,bat]:
                for gemi in gemiler:

                    if len(gemi)==0 and gemiler==pat:
                        numb+=1
                        
                        if numb==1:
                            pat2_="X - - -"
                        elif numb==2:
                            pat2_="X X - -"
                        elif numb==3:
                            pat2_="X X X -"
                        elif numb==4:
                            pat2_="X X X X"

                    if len(gemi)==0 and gemiler==bat:
                        numa+=1

                        if numa==1:
                            bat2_="X -"
                        elif numa==2:
                            bat2_="X X"

            return car2_,dest2_,sub2_,pat2_,bat2_
        def ship_Checkers():
            shipFinder_=shipFinder(carrier, destroyer, submarine, patrol_boat, battleship, car, dest, sub, pat, bat)
            shipFinder2=shipFinder(carrier2, destroyer2, submarine2, patrol_boat2, battleship2, car2, dest2, sub2, pat2, bat2)

            bothWriting("""Carrier\t\t{}\t\t\t\tCarrier\t\t{}
Battleship\t{}\t\t\t\tBattleship\t{}
Destroyer\t{}\t\t\t\tDestroyer\t{}
Submarine\t{}\t\t\t\tSubmarine\t{}
Patrol Boat\t{}\t\t\tPatrol Boat\t{}""".format(shipFinder_[0],shipFinder2[0],shipFinder_[4],shipFinder2[4],shipFinder_[1],shipFinder2[1],shipFinder_[2],shipFinder2[2],shipFinder_[3],shipFinder2[3]))
            

        def comparison():  # i am gonna do this in here: i will create a dict and add all of ships inside. Then i am going to comparison the other one.
            global table1,table2

            cnt=0
            bothWriting("Battle of Ships Game\n\n")
            while cnt<len(list_1i):
                table1,table2="",""
                global car,car2,dest,dest2,sub,sub2,bat,bat2,pat,pat2
                car,car2,dest,dest2,sub,sub2,bat,bat2,pat,pat2="-","-","-","-","-","-","- -","- -","- - - -","- - - -"        
                tex="""Round : %d\t\t\t\t\tGrid Size: 10x10\n
Player1's Hidden Board\t\tPlayer2's Hidden Board
 A B C D E F G H I J\t\t  A B C D E F G H I J\n"""%(cnt+1)
                
                shipFinder2=tuple()
                shipFinder_=tuple()
                # I am catching error for player 1
                if list_1i[cnt][1]=="" or list_1i[cnt][0]=="":
                    list_1i.remove(list_1i[cnt])
                    bothWriting("ValueError: Your input file has broken inputs.")

                if len(list_1i[cnt][1])==2:
                    list_1i.remove(list_1i[cnt])
                    bothWriting("ValueError: Your input file has broken inputs.")

                try:
                    int(list_1i[cnt][1])+4
                except:
                    pass
                else:
                    bothWriting("ValueError: Your input file has broken inputs.\n")

                    list_1i.remove(list_1i[cnt])

                


                
                bothWriting("Player1's Move\n\n")
                bothWriting(tex)
                for i,j in zip(table_Creator(table1, dict_hitted1).split("\n"), table_Creator(table2, dict_hitted2).split("\n")):
                    bothWriting(i[:-1]+"\t\t"+j[:-1]+"\n")
      
                ship_Checkers()  # this function check if ships are hitted or not
                bothWriting("\n\n")
                bothWriting("Enter your move: %s,%s\n\n"%(list_1i[cnt][0],list_1i[cnt][1]))
                         
                sub_comparison(list_1i,dict_a, dict_hitted2, carrier2,destroyer2,submarine2,battleship2,patrol_boat2,cnt)
                num1=0
                num0=0
                draw=False
                if carrier2==[] and destroyer2==[] and submarine2==[] and patrol_boat2==[[],[],[],[]] and battleship2==[[],[]]:  # if game is over or not
                    for k in [carrier,battleship,destroyer,submarine,patrol_boat]:
                        if len(k)==1:
                            num1+=1
                        elif len(k)==0:
                            num0+=1
                        if num1==1 and num0==4:
                            draw=True
                    if draw==False:
                        for i in dict_hitted1:
                                
                            if (dict_hitted1[i]=="-" or dict_hitted1[i]=="O") and dict_1[i]!="-":
                                dict_hitted1[i]=dict_1[i]

                            for i,j in zip(table_Creator(table1, dict_hitted1).split("\n"), table_Creator(table2, dict_hitted2).split("\n")):
                                bothWriting(i[:-1]+"\t\t"+j[:-1]+"\n")
                    
                else:

                    if list_ai[cnt][1]=="" or list_ai[cnt][0]=="":
                        list_ai.remove(list_ai[cnt])
                        bothWriting("ValueError: Your input file has broken inputs.")

                    if len(list_ai[cnt][1])==2:
                        list_ai.remove(list_ai[cnt])
                        bothWriting("ValueError: Your input file has broken inputs.")

                    try:
                        int(list_ai[cnt][1])+4
                    except:
                        pass
                    else:
                        bothWriting("ValueError: Your input file has broken inputs.\n")

                        list_ai.remove(list_ai[cnt])
                    bothWriting("Player2's Move\n\n")
                    bothWriting(tex)


                    for i,j in zip(table_Creator(table1, dict_hitted1).split("\n"), table_Creator(table2, dict_hitted2).split("\n")):
                        bothWriting(i[:-1]+"\t\t"+j[:-1]+"\n")
                    shipFinder_=shipFinder(carrier, destroyer, submarine, patrol_boat, battleship, car, dest, sub, pat, bat)
                    shipFinder2=shipFinder(carrier2, destroyer2, submarine2, patrol_boat2, battleship2, car2, dest2, sub2, pat2, bat2)

                    ship_Checkers()
                    bothWriting("\n\n")
                    bothWriting("Enter your move: %s,%s\n\n"%(list_ai[cnt][0],list_ai[cnt][1]))
                  
                    sub_comparison(list_ai,dict_1, dict_hitted1, carrier,destroyer,submarine,battleship,patrol_boat,cnt)

                    if carrier==[] and destroyer==[] and submarine==[] and patrol_boat==[[],[],[],[]] and battleship==[[],[]]:
                        draw=True
                        if draw==True:
                            #Draw
                            bothWriting("Draw!\n\nFinal Information\n\nPlayer1's Board\t\t\t\tPlayer2's Board\n  A B C D E F G H I J \t\t  A B C D E F G H I J\n")
                            


                        else: 
                            bothWriting("Player2 Wins!\n\nFinal Information\n\nPlayer1's Board\t\t\t\tPlayer2's Board\n  A B C D E F G H I J \t\t  A B C D E F G H I J\n")

                        for i in dict_hitted1:
                            
                            if (dict_hitted2[i]=="-" or dict_hitted2[i]=="O") and dict_a[i]!="-":
                                dict_hitted2[i]=dict_a[i]

                        for i,j in zip(table_Creator(table1, dict_hitted1).split("\n"), table_Creator(table2, dict_hitted2).split("\n")): #writing table
                            bothWriting(i[:-1]+"\t\t"+j[:-1]+"\n")
                        ship_Checkers()
                   
                    cnt+=1

        comparison()


        txt1.close()
        txt2.close()
        inp1.close()
        inp2.close()
        opt1.close()
        opt2.close()
    except :
        bothWriting("kaBOOM: run for your life!")
