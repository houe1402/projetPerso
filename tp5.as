.include "/root/SOURCES/ift209/tools/ift209.as"

.global Compile


.section ".text"

/********************************************************************************
*																				*
*	Sous-programme qui compile un arbre syntaxique et produit le code binaire  	*
*	des instructions.															*
*																				*
*	Paramètres:																	*
*		x0: adresse du noeud racine												*
*		x1: adresse du tableau d'octets pour écrire le code compilé				*
*															                    *
*	Auteurs: Elom																	*
*																				*
********************************************************************************/


Compile:
	SAVE

		mov		x2,0					//compteur qui commence à 0
		bl 		CompileRec

		bl		Write					//Appel de write
		bl		Halt					//Appel de halt

		mov		x0,x2					//on veut que x2 soit retourné dans x0 en retour
		RESTORE
		ret


CompileRec:
		SAVE
		mov		x20,x0					//x0 dans x20 pour la recursivite
		ldr		w22,[x20] 		    	//On prend les 4 premiers octets pour connaitre le type du noeud
		cmp		x22,0					//Si x22 est un nombre, on va vers l'etiquette correspondante
		b.eq	CompileRecNombre

		ldr		x0,[x20,8]				//On lit le sous-arbre de gauche et on rappelle notre methode
		bl		CompileRec

		ldr		x0,[x20,16]				//On lit le sous-arbre de droite et on rappelle notre methode
		bl		CompileRec

		b		CompileRecOperation

CompileRecNombre:
		ldr		w23,[x20,4]				//On lit le nombre du noeud
		mov		x24,0x40				//On ecrit dans le tableau
		strb	w24,[x1,x2]				//on ecrit la valeur du chiffre du neoud dans notre tableau
		add		x2,x2,1					//On ajoute 1 a x2
		rev16	w23,w23					//on reverse les bits du demi mot w23 car little endian
		str		w23,[x1,x2]				//on les ecrit dans x1
		add		x2,x2,2					//On ajoute 2 a notre compteur


		Fin:
		RESTORE
		ret


CompileRecOperation:
		ldr		w24,[x20,4]				//On lit l'operateur du neoud
		cmp		x24,0					//On regarde si x24 est un add
		b.eq	CompileRecOperationAdd
		sub		x24,x24,1
		cmp		x24,0					//On regarde si x24 est un sub
		b.eq	CompileRecOperationSub
		sub		x24,x24,1
		cmp		x24,0					//On regarde si x24 est un mul
		b.eq	CompileRecOperationMul
		b.al	CompileRecOperationDiv	//sinon x24 est un div


CompileRecOperationAdd:
		mov		x26,0x08				//On met le format add pour additionner dans x26
		b		FormatOperation
CompileRecOperationSub:
		mov		x26,0x0C				//On met le format sub pour soustraire dans x26
		b		FormatOperation

CompileRecOperationMul:
		mov		x26,0x10				//On met le format mul pour multiplier dans x26
		b		FormatOperation

CompileRecOperationDiv:
		mov		x26,0x14				//On met le format div pour diviser dans x26
		b		FormatOperation

FormatOperation:
		mov		x25,0x40				//On met l'addresse a notre format01
		orr		x25,x25,x26				//On combine le format01 avec notre code d'operateur
		strb	w25,[x1,x2]				//On le met dans notre tableau de valeurs
		add		x2,x2,1					//On ajoute 1 a notre compteur x2

		b		Fin

Write:
		SAVE
		mov		x20,0					//compteur qui commence a 0

		mov		x21,0x21				//On met 0x21 dans x21
		strb	w21,[x1,x2]				//On l'ajoute dans le tableau
		add		x2,x2,1					//On incremente x2
		mov		x0,x1					//On met x1 dans x0
		RESTORE
		ret


		Halt:
		SAVE
		mov		x21,0x00				//On ajoute	0x00 dans x21
		strb	w21,[x0,x2]				//On l'ajoute dans le tableau
		add		x2,x2,1					//On ajoute 1 a x2

	RESTORE
	ret
