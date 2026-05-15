#!/usr/bin/env python3
"""
Programa de ejemplo: Verificador de Instituciones Educativas
Este programa demuestra cómo verificar si un dominio pertenece a una institución educativa.
Creado para demostrar GitHub Copilot capabilities.
"""

import os
import json
from pathlib import Path


class EducationalDomainChecker:
    """Clase para verificar dominios educativos."""
    
    def __init__(self, domains_path="lib/domains"):
        """
        Inicializa el verificador con la ruta de dominios.
        
        Args:
            domains_path (str): Ruta donde están los archivos de dominios
        """
        self.domains_path = Path(domains_path)
        self.domains_cache = {}
    
    def load_domain(self, country_code, domain_name):
        """
        Carga un dominio específico de un país.
        
        Args:
            country_code (str): Código del país (ej: 'pe' para Perú)
            domain_name (str): Nombre del dominio (ej: 'alfabeto.txt')
        
        Returns:
            dict: Información del dominio o None si no existe
        """
        file_path = self.domains_path / country_code / "edu" / domain_name
        
        if file_path.exists():
            with open(file_path, 'r', encoding='utf-8') as f:
                lines = f.read().strip().split('\n')
                return {
                    'country': country_code,
                    'domain': domain_name,
                    'institution_name': lines[0] if lines else None,
                    'aliases': lines[1:] if len(lines) > 1 else []
                }
        return None
    
    def is_educational_domain(self, email):
        """
        Verifica si un email pertenece a una institución educativa.
        
        Args:
            email (str): Dirección de email a verificar
        
        Returns:
            bool: True si es dominio educativo, False en caso contrario
        """
        if '@' not in email:
            return False
        
        domain = email.split('@')[1]
        return self.check_domain_in_registry(domain)
    
    def check_domain_in_registry(self, domain):
        """
        Verifica si un dominio está en el registro de instituciones educativas.
        
        Args:
            domain (str): Dominio a verificar
        
        Returns:
            bool: True si está en el registro
        """
        if not self.domains_path.exists():
            return False
        
        for country_dir in self.domains_path.iterdir():
            if country_dir.is_dir():
                edu_dir = country_dir / "edu"
                if edu_dir.exists():
                    for txt_file in edu_dir.glob("*.txt"):
                        if txt_file.stem == domain.replace('.', '_'):
                            return True
        
        return False
    
    def get_institution_info(self, country_code, institution_filename):
        """
        Obtiene la información completa de una institución.
        
        Args:
            country_code (str): Código del país
            institution_filename (str): Nombre del archivo (sin .txt)
        
        Returns:
            dict: Información de la institución
        """
        result = self.load_domain(country_code, f"{institution_filename}.txt")
        return result if result else {"error": "Institution not found"}


def main():
    """Función principal para demostrar el uso."""
    
    # Crear instancia del verificador
    checker = EducationalDomainChecker()
    
    # Ejemplo 1: Cargar dominio peruano
    print("=" * 60)
    print("Programa Verificador de Instituciones Educativas")
    print("=" * 60)
    print()
    
    # Intentar cargar Alfabeto
    alfabeto_info = checker.get_institution_info("pe", "alfabeto")
    print("Información de Alfabeto SAC:")
    print(json.dumps(alfabeto_info, indent=2, ensure_ascii=False))
    print()
    
    # Ejemplo 2: Verificar emails
    test_emails = [
        "estudiante@alfabeto.edu.pe",
        "usuario@gmail.com",
        "profesor@unaab.edu.ng"
    ]
    
    print("Verificación de emails educativos:")
    print("-" * 60)
    for email in test_emails:
        is_edu = checker.is_educational_domain(email)
        status = "✓ Educativo" if is_edu else "✗ No educativo"
        print(f"{email:<35} {status}")
    print()
    
    print("=" * 60)
    print("Programa finalizado correctamente")
    print("=" * 60)


if __name__ == "__main__":
    main()
