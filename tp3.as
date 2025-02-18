/********************************************************************************
*																				*
*	Programme qui lit, affiche et vérifie un sudoku.                          	*
*																				*
*															                    *
*	Auteurs: 																	*
*																				*
********************************************************************************/

.include "/root/SOURCES/ift209/tools/ift209.as"

.global Main

.section ".text"


/* Début du programme */


Main:	            adr		x20,Sudoku          //x20 contient l'adresse de base du sudoku

			        mov		x0,x20              //Paramètre: adresse du sudoku
			        bl      LireSudoku			//Appelle le sous-programme de lecture

					mov		x0,x20				//Paramètre: adresse du sudoku
					bl		AfficherSudoku		//Appelle le sous-programme d'affichage

					mov		x0,x20				//Paramètre: adresse du sudoku
					bl		VerifierSudoku		//Appelle le sous-programme de vérification

					mov		  x0,#0				//0: tous les tampons
					bl        fflush			//Vidange des tampons

					mov		  x0,#0				//0: aucune erreur
					bl        exit				//Fin du programme

LireSudoku:
             SAVE
					mov x21, 0 //i pour incrementer la boucle
					mov x20,x0 //adresse du sudoku dans x20
					mov x1,x20

Lire10:
					cmp x21,81
					b.ge LireExit

					adr x0, scfmt1
					add x1,x20,x21 // on mets dans x1 la valeur de l'adresse du sudoku, et on incremente pour lire les valeurs suivantes ensuite
					bl scanf
					add x21,x21,1 //i++
					b.al Lire10

LireExit:
				RESTORE
		        ret


AfficherSudoku:
        		SAVE
					mov x21, 0 //i pour incrementer la boucle
					mov x20,x0

Afficher10:
					cmp x21,81
					b.ge AfficherExit

					adr x0, sautLigne //on veut un saut de ligne avant l'affichage de la ligne du sudoku
					bl printf

					adr x0, bordureLigne //quand on veut afficher le sudoku, on commence par afficher la bordure du haut de bloc
					bl printf
					mov x23,0

Afficher12:
					cmp x23,3
					b.ge Afficher10

					adr x0, sautLigne //on veut un saut de ligne avant l'affichage de la ligne du sudoku
					bl printf

					adr x0, barre //on veut afficher la barre avant l'affichage de la ligne du sudoku
					bl printf

					mov x22,0 //servira a savoir si on a changé de bloc ou pas
					add x23,x23,1

Afficher20:
					cmp x22,3 // savoir si on a changé de bloc ou pas
					b.ge Afficher12

					adr x0, scfmt1
					ldrb w1,[x20,x21]
					bl printf
					add x21,x21,1

					adr x0, scfmt1
					ldrb w1,[x20,x21]
					bl printf
					add x21,x21,1

					adr x0, scfmt1
					ldrb w1,[x20,x21]
					bl printf
					add x21,x21,1

					adr x0, barre //on veut afficher la barre apres l'affichage de 3 chiffres du sudoku
					bl printf

					add x22,x22,1 // on incrémente x22 pour suivre les blocs de trois chiffres
					b.al Afficher20

AfficherExit:

					adr x0, sautLigne //on veut un saut de ligne avant l'affichage de la ligne du sudoku
					bl printf

					adr x0, bordureLigne //on veut afficher la bordure pour clore l'affichage du sudoku
					bl printf

					adr x0, sautLigne //on veut un saut de ligne avant l'affichage de la ligne du sudoku
					bl printf

		        RESTORE
				ret


VerifierSudoku:
		        SAVE   //on va utiliser en majorité trois sous programmes differents pour verifier lignes, colonnes et blocs
					mov x20,x0

					mov x0,x20
					bl VerifierSudokuL10
					mov x0,x20
					bl VerifierSudokuC10
					mov x0,x20
					bl VerifierSudokuB10

					bl VerifierExit

VerifierSudokuL10:
				SAVE  //on verifie les lignes

					mov x21,0 //i pour incrementer
					mov x22,x0

VerifierSudokuL12:
					cmp	x21,9
					b.ge VerifierExit
					adr	x0,Tableau
					bl VerifierSudokuT10

					mov	x23,x0
					mov	x0,x22
					mov	x1,x23
					mov	x25,9
					mul x24,x21,x25	//(i*n)
					mov	x2,x24
					mov	x3,x21

					bl VerifierSudokuL20
					b.al iterationLigne

VerifierSudokuL20:
		SAVE  //On continue de vérifier les lignes
					mov	x20,x0
					mov	x21,x1
					mov	x22,x2
					mov	x25,0

VerifierSudokuL30:
					cmp	x25,9
					b.ge VerifierExit
					add	x26,x22,x25
					ldrb w23, [x20,x26]
					ldrb w24, [x21,x23]
					add	x24,x24,1
					cmp	x24,1
					b.gt messageLigne //s'il y a une erreur, on affiche le message

					strb w24,[x21,x23]
					add	x25,x25,1
					b.al VerifierSudokuL30

iterationLigne:
					add	x21,x21,1
					bl VerifierSudokuL12

messageLigne:
					adr	x0,erreurLigne
					add	x19,x3,1 //ligne++
					mov x1,x19
					bl printf

					adr	x0,sautLigne
					bl printf

					bl VerifierExit

VerifierSudokuC10:
		      SAVE  //on verifie les colonnes

					mov	x21,0 //i pour incrementer
					mov	x22,x0

VerifierSudokuC12:
					cmp	x21,9
					b.ge VerifierExit
					adr	x0,Tableau
					bl VerifierSudokuT10

					mov x23,x0
					mov	x0,x22
					mov	x1,x23
					mov	x2,x21

					bl VerifierSudokuC20
					b.al iterationColonne

VerifierSudokuC20:
		SAVE
					mov	x20,x0
					mov	x21,x1
					mov	x22,x2
					mov	x25,0
					mov	x26,x22

VerifierSudokuC30:
					cmp	x25,9
					b.ge VerifierExit
					ldrb w23, [x20,x26]
					ldrb w24, [x21,x23]
					add	x24,x24,1
					cmp	x24,1
					b.gt messageColonne //s'il y a une erreur, on affiche le message

					strb w24,[x21,x23]
					add	x25,x25,1
					add	x26,x26,9
					b.al VerifierSudokuC30

iterationColonne:
					add	x21,x21,1
					bl VerifierSudokuC12

messageColonne:
					adr	x0,erreurColonne
					add	x19,x22,1 //ligne ++
					mov	x1,x19
					bl printf

					adr	x0,sautLigne
					bl printf

					bl VerifierExit

VerifierSudokuB10:
		SAVE //on verifie les blocs
					mov	x21,0 //i pour incrementer
					mov	x22,x0
					mov	x25,0
					mov	x27,0
					mov	x28,0

VerifierSudokuB12:
					cmp	x21,9
					b.ge VerifierExit
					adr	x0,Tableau
					bl VerifierSudokuT10

					mov	x23,x0
					mov	x0,x22
					mov	x1,x23
					mov	x24,3
					mul	x25,x21,x24
					add	x25,x25,x28
					mov	x2,x25
					mov	x3,x21

					bl VerifierSudokuB20
					b.al iterationBloc

VerifierSudokuB20:
		SAVE
					mov	x20,x0
					mov	x21,x1
					mov	x22,x2
					mov	x25,0
					mov	x26,x22

VerifierSudokuB30:
					cmp	x25,9
					b.ge VerifierExit

					ldrb w23, [x20,x26]
					ldrb w24, [x21,x23]
					add	x24,x24,1
					cmp	x24,1
					b.gt messageBloc //s'il y a une erreur, on affiche le message

					strb w24,[x21,x23]
					add	x25,x25,1
					add	x26,x26,1

					ldrb w23, [x20,x26]
					ldrb w24, [x21,x23]
					add	x24,x24,1
					cmp	x24,1
					b.gt messageBloc

					strb w24,[x21,x23]
					add	x25,x25,1
					add	x26,x26,1

					ldrb w23, [x20,x26]
					ldrb w24, [x21,x23]
					add	x24,x24,1
					cmp	x24,1
					b.gt messageBloc

					strb w24,[x21,x23]
					add	x25,x25,1
					add	x26,x26,7

		          b.al VerifierSudokuB30

iterationBloc:
				 add	x21,x21,1
				 mov	x27,x21

				udiv x27,x27,x24
				mul	x27,x27,x24
				cmp	x27,x21
				b.ne VerifierSudokuB12

				add	x28,x28,18
				b.al VerifierSudokuB12

messageBloc:
				adr	x0, erreurBloc

				add	x19,x3,1 //ligne ++
				mov	x1,x19
				bl printf

				adr x0,sautLigne
				bl printf

				bl VerifierExit

VerifierSudokuT10:
		SAVE
				mov	x20,0

VerifierSudokuT12:
				cmp	x20,9
				b.gt VerifierExit
				strb wzr,[x0,x20] //stock valeur 0 à l'adresse 'x0' décalé par 'x20'
				add	x20,x20,1
				b.al VerifierSudokuT12

VerifierExit:
        RESTORE
		ret


.section ".rodata"

scfmt1: .asciz  " %d "
bordureLigne: .asciz  "|---------|---------|---------|"
sautLigne: .asciz  "\n"
barre: .asciz	"|"
erreurLigne: .asciz	"Le sudoku contient une erreur dans la ligne %d"
erreurColonne: .asciz	"Le sudoku contient une erreur dans la colonne %d"
erreurBloc:	.asciz	"Le sudoku contient une erreur dans le bloc %d"

.section ".bss"
Sudoku: 	.skip 81
Tableau:	.skip 9
