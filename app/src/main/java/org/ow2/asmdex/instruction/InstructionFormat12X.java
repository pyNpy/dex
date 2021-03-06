/* Software Name : AsmDex
 * Version : 1.0
 *
 * Copyright © 2012 France Télécom
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.ow2.asmdex.instruction;

import org.ow2.asmdex.lowLevelUtils.ByteVector;
import org.ow2.asmdex.structureWriter.ConstantPool;

/**
 * Encodes and decodes an instruction built with the Dalvik format 12X.
 * 
 * @author Julien Névo
 */
public class InstructionFormat12X extends Instruction implements ITwoRegistersInstruction {

	private static final int INSTRUCTION_SIZE = 2; // Instruction size in bytes.
	
	private int registerA;
	private int registerB;
	
	@Override
	public int getRegisterA() {
		return registerA;
	}

	@Override
	public int getRegisterB() {
		return registerB;
	}

	/**
	 * Returns the RegisterA encoded in the given 16-bit opcode.
	 * @return the RegisterA .
	 */
	public static int getRegisterA(int opcode) {
		return (opcode >> 8) & 0xf;
	}
	
	/**
	 * Returns the RegisterB encoded in the given 16-bit opcode.
	 * @return the RegisterB .
	 */
	public static int getRegisterB(int opcode) {
		return (opcode >> 12) & 0xf;
	}

	/**
	 * Constructor of the Instruction by providing all the elements it's composed of.
	 * @param opcode 8 or 16 bits opcode.
	 * @param destinationRegister the destination register.
	 * @param var the register.
	 */
	public InstructionFormat12X(int opcode, int destinationRegister, int var) {
		super(opcode);
		registerA = destinationRegister;
		registerB = var;
		Instruction.test4BitsLimit(registerA);
		Instruction.test4BitsLimit(registerB);
	}
	
//	@Override
//	public int[] getUsedRegisters() {
//		int[] usedRegisters;
//		// Pair registers or not ?
//		switch (opcodeByte) {
//		case 0x04:
//		case 0x7d:
//		case 0x7e:
//		case 0x80:
//		case 0x81:
//		case 0x83:
//		case 0x86:
//		case 0x88:
//		case 0x89:
//		case 0x8b:
//		case 0x8c:
//		case 0xbb:
//		case 0xbc:
//		case 0xbd:
//		case 0xbe:
//		case 0xbf:
//		case 0xc0:
//		case 0xc1:
//		case 0xc2:
//		case 0xc3:
//		case 0xc4:
//		case 0xc5:
//		case 0xcb:
//		case 0xcc:
//		case 0xcd:
//		case 0xce:
//		case 0xcf:
//			usedRegisters = new int[] { registerA, registerA + 1, registerB, registerB + 1 };
//			break;
//		default:
//			usedRegisters = new int[] { registerA, registerB };
//		}
//		
//		return usedRegisters;
//	}

//	@Override
//	public int getNbEncodedRegisters() {
//		return 2;
//	}

	@Override
	public int getSize() {
		return INSTRUCTION_SIZE;
	}
	
	@Override
	public void write(ByteVector out, ConstantPool constantPool) {
		int firstShort = ((registerB & 0xf) << 12) + ((registerA & 0xf) << 8) +	opcodeByte;
		out.putShort(firstShort);
	}

}
